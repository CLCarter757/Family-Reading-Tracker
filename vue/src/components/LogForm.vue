<template>

    <div>
        <p>Reading Activity for {{ this.$route.params.book.title }}</p>

    
  <form class="log-reading-form" v-on:submit.prevent = "logReading">
      <div class="form-style-5">

<fieldset>

<label for="field3"><span>Date <span class="required">*</span></span><input type="date" class="input-field" name="field3" v-model="readingActivity.date" value="" /></label><br>
<label for="field2"><span>Minutes<span class="required">*</span></span><input type="text" class="input-field" name="field2" value="" v-model="readingActivity.time"/></label><br>
<label for="field4"><span>Format</span><select name="field4" class="select-field"><br>
<option value="paper">Paper</option><br>
<option value="audio-book">Audio Book</option><br>
<option value="read-aloud(reader)">Read-Aloud(Reader)</option><br>
<option value="listener">Read-Aloud(Listener)</option><br>
<option value="other">Other</option><br>
</select></label>
</fieldset>
<fieldset>
<label for="field6"><span>Notes <span class="required">*</span></span><textarea name="field6" class="textarea-field" v-model="readingActivity.notes"></textarea></label><br>
<label><span> </span><input type="submit" value="Submit" /></label>
</fieldset>

</div>
  </form>
    </div>
</template>

<script>
import BookService from '../services/BookService';
export default {
    name: "log-form",
    props: {
        book: Object
    },
    data() {
        return {
            readingActivity: {
                date: '',
                format: '',
                time: '',
                notes: ''
            }
        }
    },
    methods: {
        logReading() {
            const newReadingActivity = {
                userBookId: this.$route.params.book.userBookId,
                dateCreated: this.readingActivity.date,
                reader: this.$route.params.book.userId,
                format: this.readingActivity.format,
                time: Number(this.readingActivity.time),
                notes: this.readingActivity.notes
            }


            
            BookService.submitReadingActivity(this.$route.params.book.userId, newReadingActivity).then((response) => {
                if(response.status === 201) {
                    alert('Reading Activity Logged!')

                    this.readingActivity = {
                    date: '',
                    format: '',
                    time: '',
                    notes: ''
                    };
                    this.$router.push('/');
                    }
                    //this.$router.go();
            })
            
            

        }
        
    }
}
</script>

<style type="text/css">
.form-style-5{
	max-width: 500px;
	padding: 10px 20px;
	background: #bbdefb;
	margin: 10px auto;
	padding: 20px;
	background: #bbdefb;
	border-radius: 8px;
	font-family: Georgia, "Times New Roman", Times, serif;
}
.form-style-5 fieldset{
	border: none;
}
.form-style-5 legend {
	font-size: 1.4em;
	margin-bottom: 10px;
}
.form-style-5 label {
	display: block;
	margin-bottom: 8px;
}
.form-style-5 input[type="text"],
.form-style-5 input[type="date"],
.form-style-5 input[type="datetime"],
.form-style-5 input[type="email"],
.form-style-5 input[type="number"],
.form-style-5 input[type="search"],
.form-style-5 input[type="time"],
.form-style-5 input[type="url"],
.form-style-5 textarea,
.form-style-5 select {
	font-family: Georgia, "Times New Roman", Times, serif;
	background: rgba(255,255,255,.1);
	border: none;
	border-radius: 4px;
	font-size: 15px;
	margin: 0;
	outline: 0;
	padding: 10px;
	width: 100%;
	box-sizing: border-box; 
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box; 
	background-color: #e8eeef;
	color:#8a97a0;
	-webkit-box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
	box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
	margin-bottom: 30px;
}
.form-style-5 input[type="text"]:focus,
.form-style-5 input[type="date"]:focus,
.form-style-5 input[type="datetime"]:focus,
.form-style-5 input[type="email"]:focus,
.form-style-5 input[type="number"]:focus,
.form-style-5 input[type="search"]:focus,
.form-style-5 input[type="time"]:focus,
.form-style-5 input[type="url"]:focus,
.form-style-5 textarea:focus,
.form-style-5 select:focus{
	background: #d2d9dd;
}
.form-style-5 select{
	-webkit-appearance: menulist-button;
	height:35px;
}
.form-style-5 .number {
	background: #1abc9c;
	color: #fff;
	height: 30px;
	width: 30px;
	display: inline-block;
	font-size: 0.8em;
	margin-right: 4px;
	line-height: 30px;
	text-align: center;
	text-shadow: 0 1px 0 rgba(255,255,255,0.2);
	border-radius: 15px 15px 15px 0px;
}

.form-style-5 input[type="submit"],
.form-style-5 input[type="button"]
{
	position: relative;
	display: block;
	padding: 19px 39px 18px 39px;
	color: #FFF;
	margin: 0 auto;
	background: #64b5f6;
	font-size: 18px;
	text-align: center;
	font-style: normal;
	width: 100%;
	border: 1px solid #64b5f6;
	border-width: 1px 1px 3px;
	margin-bottom: 10px;
}
.form-style-5 input[type="submit"]:hover,
.form-style-5 input[type="button"]:hover
{
	background: #109177;
}
</style>
