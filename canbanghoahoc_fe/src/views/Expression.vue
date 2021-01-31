<template>
  <div :class="'expression_'+expressionName">
    <h3>{{expressionName.toUpperCase()}}</h3>
    <form action @submit.prevent="onSubmit">
      <input v-model="compoundInTextarea" />
      <button type="submit" class="btn btn-info">ADD {{expressionName.toUpperCase()}}</button>
    </form>
    <div class="list_compounds">
      <span v-for="(compound) in compoundsShowed" :key="compound" :class="compound">
        <compound :details="compound"></compound>
      </span>
    </div>
  </div>
</template>

<script>
import Compound from "./Compound.vue";

export default {
  name: "expression",
  props: ["expressionName"],
  data() {
    return {
      expressionToSubmit: "",
      compoundInTextarea: "",
      compoundsShowed: []
    };
  },
  components: { Compound },
  methods: {
    /** Use when complete typing a compound in text area */
    onSubmit() {
      this.compoundsShowed.push(this.compoundInTextarea);
      this.expressionToSubmit = this.compoundsShowed.join("+");
      // console.log(this.expressionToSubmit);
      this.compoundInTextarea = "";
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
    expressionToSubmit(newData) {
      this.$parent.updateExpression(this.expressionName, newData);
    }
  }
};
</script>

<style scoped>
input {
  border: 2px purple solid;
}
</style>