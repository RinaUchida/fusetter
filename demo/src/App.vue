<template>

<div id="app">

	<HeaderItem />

	<!--Main !-->
	<router-view></router-view>

	<FooterItem />

</div>
</template>

<script>
	import HeaderItem from './views/HeaderItem'
	import FooterItem from './views/FooterItem'
	import 'normalize.css'
	import './CSS/common.css'

	export default {
	  name: 'app',
	  components : {
	    HeaderItem,
	    FooterItem
	  }
	}

</script>

<style>
</style>
