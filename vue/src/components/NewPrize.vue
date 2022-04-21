<template>

    <div>
        <br>
        <p class="has-text-weight-bold is-size-2"> Create a New Prize for the Family!</p>

    
  <form class="log-reading-form" v-on:submit.prevent="newPrize">
      <div class="form-style-5">

<fieldset>

    <label for="field1"><span>Prize Name <span class="required">*</span></span><input name="field1" class="input" v-model="prizeInput.name" /></label><br>
    <label for="field2"><span>Description <span class="required">*</span></span><textarea name="field2" class="textarea-field" v-model="prizeInput.description"></textarea></label><br>
    <label for="field3"><span>Milestone<span class="required">*</span></span><input type="number" class="input-field" name="field3" value="Minutes" v-model="prizeInput.milestone" /></label><br>
    <label for="field4"><span>User Group</span><select name="field4" class="select-field" v-model="prizeInput.userGroup"><br>
    <option selected="selected" value="all">All</option><br>
    <option value="parents">Parents Only</option><br>
    <option value="children">Children</option><br>
    </select></label>
    <label for="field5"><span>Max Prizes Available<span class="required">*</span></span><input type="number" class="input-field" name="field5" value="Max Prizes" v-model="prizeInput.maxPrizes" /></label><br>
    <label for="field6"><span>Start Date <span class="required">*</span></span><input type="date" class="input-field" name="field6" v-model="prizeInput.startDate" /></label><br>
    <label for="field7"><span>End Date <span class="required">*</span></span><input type="date" class="input-field" name="field7" v-model="prizeInput.endDate"  /></label><br>

</fieldset>
<fieldset>
<label><span> </span><input type="submit" value="Create Prize" /></label>
</fieldset>

</div>
  </form>
    </div>
</template>

<script>
import PrizeService from '../services/PrizeService';
export default {
    name: "log-form",
    data() {
        return {
            prizeInput: {
                name: "",
                description: "",
                milestone: "",
                userGroup: "",
                maxPrizes: "",
                startDate: "",
                endDate: ""
            }
        }
    },
    methods: {
        newPrize() {
            const newPrize = {
                familyId: this.$store.state.user.familyId,
                name: this.prizeInput.name,
                description: this.prizeInput.description,
                milestone: Number(this.prizeInput.milestone),
                userGroup: this.prizeInput.userGroup,
                maxPrizes: Number(this.prizeInput.maxPrizes),
                startDate: this.prizeInput.startDate,
                endDate: this.prizeInput.endDate
            }

            PrizeService.createPrize(newPrize).then((response) => {
                if(response.status === 201) {
                    alert('New Prize Created!')

                    this.prizeInput = {
                    name: "",
                    description: "",
                    milestone: "",
                    userGroup: "",
                    maxPrizes: "",
                    startDate: "",
                    endDate: ""
                    }
                    this.$router.push('/prizes');
                    } else {
                        alert('CRITICAL FAILURE')
                    }
                    
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
