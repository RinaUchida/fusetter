<template>

<div class="wrapper">
	<div class="wrapper_inner">
		<div class="fse_box fse_lock">

			<div class="fse_message">{{ message }}</div>

		</div>
	</div>
</div>

</template>

<script>

  import axios from 'axios'


  export default {

    name: "message",
    data () {
      return {
        message: ''
      }
    },
    created: async function () {
      await this.refresh()
    },
    methods: {

      refresh: async function () {
        console.info(this.$route.params.fsgCryptogram);

        var fsgCryptogram = this.$route.params.fsgCryptogram;

        var base_url = 'http://localhost:8888//FussageGetByFsgCryptogram//'
        var forwardUrl = base_url + fsgCryptogram;


        //axios.get(forwardUrl).then(res => {
        //	this.message = res.data.turnTweet
		//}).catch(error => {
		//	this.message = error.data.status + ":" + error.data.error ":" + error.data.message
		//});

        axios.get(forwardUrl)
        .then(function (response) {
            console.log(response);
            this.message = response.data.turnTweet
        })
        .catch(function (response) {
            console.log(response);
        });


      }
    }
  }
</script>

<style scoped>
</style>