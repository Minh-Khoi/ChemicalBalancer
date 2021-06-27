<template>
  <div class="modal_equation">
    <span class="equation_info">  </span>
    <ion-item color="success">WIPE DOWN TO CLOSE</ion-item>

  </div>
</template>

<script>
import {IonItem} from '@ionic/vue';
export default {
  props:["equationInfo"],
  components:{IonItem},
  mounted(){
    document.querySelector(".modal_equation .equation_info").innerHTML = this.equationInfo;
    document.querySelector(".modal_equation .equation_info a").display = none
  }
}

</script>

<style scoped>

</style>