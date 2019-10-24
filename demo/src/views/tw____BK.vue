<template>

<div class="wrapper">
	<div class="wrapper_inner">
		<div class="fse_box fse_lock">

			<div class="fse_message">{{ message }}</div>

		</div>

		<!-- 認証メッセージ -->
		<div class="fse_lock_message">
			<img src="../img/lock.png">
			<div>
				日本語べた打ち：伏せられた内容を見るには、Twitterの認証が必要です。</BR>
				伏せ字ツイートの公開範囲によっては、見られない場合もあります。
			</div>

			<div class="fse_buttons">
				<a href="">連携アプリの認証が不安な方は、こちらをお読みください</a><br>
				<!-- <a class="button" v-bind:href="refererURL">伏せられた内容を見る！</a> -->
				<a class="button" v-on:click="handleClick()">伏せられた内容を見る！</a>
			</div>
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
        message: '',
        //refererURL : "/"
      }
    },
    created: async function () {
      await this.refresh()
    },
    methods: {


      refresh: async function () {

        var fsgCryptogram = this.$route.params.fsgCryptogram;
        //this.refererURL ="http://localhost:8888//auth?r="+unescape(encodeURIComponent(this.$route.fullPath));
        //this.refererURL ="http://fusetter.com//auth?r="+unescape(encodeURIComponent(this.$route.fullPath));


        //TODO ${fsgCryptogram}で書き換えられそう
        var base_url = 'http://localhost:8888//FussageGetByFsgCryptogram//'
        var forwardUrl = base_url + fsgCryptogram;

        //TODO ここ共通関数化する。自作エラーのみ（JSONにメッセージがあるとか）
        var parent = this;
        axios({
            method  : 'GET',
            url     : forwardUrl,
            // 500系以外は正常として扱う（thenで処理できるようにする）.
            validateStatus: function (status) {
                return status < 500;
            },
        })
        .then(function (response) {
        	if(response.status === 404){

        		//Debug用出力ログ
        		//console.log(response);
                //parent.message = response.data.status + ":" + response.data.error + ":" + response.data.message;

                //404エラーページに遷移
                parent.$router.push("/404")

        	}else{
        		console.log(response);

        		if(response.data.show_auth){

                	parent.message = response.data.turnTweet

        		}else{

        			//TODO オリジナルツイートでいいのでは
        			parent.message = response.data.turnTweet

        		}

        	}
        })
        .catch(function (error) {
        	console.log(error);
        	parent.message = error.message;
        });

      },

      handleClick: function(event){

    	//this.refererURL ="http://fusetter.com//auth?r="+unescape(encodeURIComponent(this.$route.fullPath));
    	var refererURL ="http://fusetter.com//auth?r="+unescape(encodeURIComponent(this.$route.fullPath));

    	axios({
            method  : 'GET',
            url     : refererURL,
            xsrfHeaderName: 'Access-Control-Allow-Origin',
            withCredentials: true,
            // 500系以外は正常として扱う（thenで処理できるようにする）.
            validateStatus: function (status) {
                return status < 500;
            },
        })
        .then(function (response) {
        	if(response.status === 404){

        		//Debug用出力ログ
        		//console.log(response);
                //parent.message = response.data.status + ":" + response.data.error + ":" + response.data.message;

                //404エラーページに遷移
                parent.$router.push("/404")

        	}else{

        		console.log(response);


        		location.href=response.data.URL;

        	}
        })
        .catch(function (error) {
        	console.log(error);
        	parent.message = error.message;
        });

      }

    }




  }
</script>

<style scoped>
</style>