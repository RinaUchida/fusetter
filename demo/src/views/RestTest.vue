<template>
	<h1>{{ message }}</h1>

</template>

<script>
	import axios from 'axios'

  export default {
    name: "message",
    data () {
      return {
        message: []
      }
    },
    created: async function () {
      await this.refresh()
    },
    methods: {
      refresh: async function () {
        const res = await axios.get('http://localhost:8888/api/items2')
        this.message = res.data
        console.info(this.message)
      }
    }
  }
</script>

<style scoped>

</style>