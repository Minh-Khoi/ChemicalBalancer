<template>
  <span :id="'modal_of_'+title">
    <h3 >{{compoundInfo.formula}}</h3>
    <h5>Name: {{compoundInfo.vietnamese_name}}</h5>
    <p v-for="info in compoundInfo.moreInfo" :key="info.id">
      {{ info }}
      <br />
    </p>
    <ion-item color="success">WIPE DOWN TO CLOSE</ion-item>
  </span>
</template>

<script>
import {IonItem, modalController} from '@ionic/vue';
export default {
  name:"compound_modal",
  components:{ IonItem },
  props:["compoundDetails", "title"],
  data(){
    return{
      compoundInfo : this.compoundDetails
    }
  },
  methods:{
    // dismissModal(){
    //   modalController.dismiss(undefined,undefined,'modal_of_'+this.title)
      
    // }
  },
  mounted(){
    console.log(this.title);
  }
}
</script>

<style scoped>
h3,h5{
  color:red;
  font-weight: 800;
  text-align: center;
}
</style>