<template>
  <span :class="'expression_'+expressionName">
    <form action="" @submit.prevent="onSubmit">
      <ion-item class="box">
        <ion-label  color="primary">
          {{expressionName.toUpperCase()}}
        </ion-label>
        <ion-input @input="onIonInputEntered($event)" ></ion-input>
      </ion-item>
    </form>
    <span v-for="(compound,index) in compoundsShowed" :key="index" :class="compound">
      <compound :details="compound"></compound>
      <!-- <span>FUCK</span> -->
    </span>
  </span>
</template>

<script>
import Compound from "./Compound.vue";
import {IonInput, IonItem, IonLabel,} from '@ionic/vue';

export default {
  name: "expression",
  props: ["expressionName"],
  components:{Compound, IonInput, IonItem, IonLabel,},
  data() {
    return {
      expressionToSubmit: "",
      compoundInTextarea: "",
      compoundsShowed: []
    };
  },
  methods: {
    /** when the ion_input Entered */
    onIonInputEntered(event){
      this.compoundInTextarea=event.target.value;
    },

    /** Use when complete typing a compound in text area */
    onSubmit() {
      this.compoundsShowed.push(this.compoundInTextarea);
      this.expressionToSubmit = this.compoundsShowed.join("+");
      console.log(this.compoundsShowed);
      this.compoundInTextarea = "";
      document.querySelector(".expression_"+this.expressionName+" ion-input").value = ""
    },

    /** Delete one compound from Expression */
    deleteCompound(de) {
      for (let i = 0; i < this.compoundsShowed.length; i++) {
        if (this.compoundsShowed[i] == de) {
          this.compoundsShowed.splice(i, 1);
          this.expressionToSubmit = this.compoundsShowed.join("+");
          break;
        }
      }
    }
  },
  watch: {
    /** To update the fetching datas when the Compound Components have changed */
    expressionToSubmit(newData) {
      console.log(this.$parent)
      this.$parent.updateExpression(this.expressionName, newData);
      
    }
  }

}
</script>

<style scoped>
ion-label{
  font-weight: 800;
}
.box {
    border-color: #403E39;
    border-width: thin;
    border-style: solid;
    border-radius: 3px;
}
</style>

