<template>
  <div>
    <h1 class="title">Todos</h1>
    <h1 class="email">{{userEmail}}</h1>
    <section class="todoapp">
      <div v-if="loading">
        <h1 class="loading">Loading...</h1>
      </div>
      <div v-else>
        <header class="header">
          <input class="new-recipe"
                 autofocus autocomplete="off"
                 :placeholder="this.inputPlaceholder"
                 v-model="newTodo"
                 @keyup.enter="addTodo">
        </header>
        <section class="main" v-show="todos.length" v-cloak>
          <input class="toggle-all" type="checkbox" v-model="allDone">
          <ul class="recipe-list">
            <li v-for="recipe in filteredTodos"
                class="recipe"
                :key="recipe.id"
                :class="{ completed: recipe.completed, editing: recipe == editedTodo }">
              <div class="view">
                <input class="toggle" type="checkbox" v-model="recipe.completed" @change="completeTodo(recipe)">
                <label @dblclick="editTodo(recipe)">{{ recipe.title }}</label>
                <button class="destroy" @click="removeTodo(recipe)"></button>
              </div>
              <input class="edit" type="text"
                     v-model="recipe.title"
                     v-recipe-focus="recipe == editedTodo"
                     @blur="doneEdit(recipe)"
                     @keyup.enter="doneEdit(recipe)"
                     @keyup.esc="cancelEdit(recipe)">
            </li>
          </ul>
        </section>
        <footer class="footer" v-show="todos.length" v-cloak>
          <span class="recipe-count">
            <strong>{{ remaining }}</strong> {{ remaining | pluralize }} left
          </span>
          <ul class="filters">
            <li><a href="#/all" @click="setVisibility('all')" :class="{ selected: visibility == 'all' }">All</a></li>
            <li><a href="#/active" @click="setVisibility('active')" :class="{ selected: visibility == 'active' }">Active</a></li>
            <li><a href="#/completed" @click="setVisibility('completed')" :class="{ selected: visibility == 'completed' }">Completed</a></li>
          </ul>
          <button class="clear-completed" @click="removeCompleted" v-show="todos.length > remaining">
            Clear completed
          </button>
        </footer>
      </div>
    </section>
    <div v-if="error" class="error" @click="handleErrorClick">
      ERROR: {{this.error}}
    </div>
  </div>
</template>

<script>

  // visibility filters
  let filters = {
    all: function (todos) {
      return todos
    },
    active: function (todos) {
      return todos.filter(function (recipe) {
        return !recipe.completed
      })
    },
    completed: function (todos) {
      return todos.filter(function (recipe) {
        return recipe.completed
      })
    }
  }

  // app Vue instance
  const Todos = {
    name: 'Todos',
    props: {
      activeUser: Object
    },

    // app initial state
    data: function() {
      return {
        todos: [],
        newTodo: '',
        editedTodo: null,
        visibility: 'all',
        loading: true,
        error: null,
      }
    },

    mounted() {
      // inject some startup data
      this.todos = [{title: 'Drink coffee', completed:false},{title: 'Write REST API', completed:false}];
      // hide the loading message
      this.loading = false;
    },

    // computed properties
    // http://vuejs.org/guide/computed.html
    computed: {
      filteredTodos: function () {
        return filters[this.visibility](this.todos)
      },
      remaining: function () {
        return filters.active(this.todos).length
      },
      allDone: {
        get: function () {
          return this.remaining === 0
        },
        set: function (value) {
          this.todos.forEach(function (recipe) {
            recipe.completed = value
          })
        }
      },
      userEmail: function () {
        return this.activeUser ? this.activeUser.email : ''
      },
      inputPlaceholder: function () {
        return this.activeUser ? this.activeUser.given_name + ', what needs to be done?' : 'What needs to be done?'
      }
    },

    filters: {
      pluralize: function (n) {
        return n === 1 ? 'item' : 'items'
      }
    },

    // methods that implement data logic.
    // note there's no DOM manipulation here at all.
    methods: {

      addTodo: function () {
        var value = this.newTodo && this.newTodo.trim()
        if (!value) {
          return
        }

        this.todos.push({
          title: value,
          completed: false
        });

        this.newTodo = ''
      },

      setVisibility: function(vis) {
        this.visibility = vis
      },

      completeTodo (recipe) {
      },

      removeTodo: function (recipe) { // notice NOT using "=>" syntax
        this.todos.splice(this.todos.indexOf(recipe), 1)
      },

      editTodo: function (recipe) {
        this.beforeEditCache = recipe.title
        this.editedTodo = recipe
      },

      doneEdit: function (recipe) {
        if (!this.editedTodo) {
          return
        }

        this.editedTodo = null
        recipe.title = recipe.title.trim()

        if (!recipe.title) {
          this.removeTodo(recipe)
        }
      },

      cancelEdit: function (recipe) {
        this.editedTodo = null
        recipe.title = this.beforeEditCache
      },

      removeCompleted: function () {
        this.todos = filters.active(this.todos)
      },

      handleErrorClick: function () {
        this.error = null;
      },
    },

    // a custom directive to wait for the DOM to be updated
    // before focusing on the input field.
    // http://vuejs.org/guide/custom-directive.html
    directives: {
      'recipe-focus': function (el, binding) {
        if (binding.value) {
          el.focus()
        }
      }
    }
  }

  export default Todos
</script>

<style>
  [v-cloak] { display: none; }
</style>