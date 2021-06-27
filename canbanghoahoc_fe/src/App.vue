<template>
  <div id="app2">
    <h1 class="h1 text-center">Chemical equation balancing</h1>
    <expression expressionName="reactant"></expression>
    <h1>===========>>>></h1>
    <expression expressionName="product"></expression>

    <button
      class="btn btn-block bg-secondary text-light fixed-bottom"
      @click="doBalance()"
    >MAKE THE EQUATIONS BALANCED</button>
    <div class="equation_details" ref="equation_details"></div>
  </div>
</template>

<script>
import Expression from "./views/Expression";
import { backendURL, browser_domain } from "./router/server_route";

export default {
  components: {
    Expression
  },
  data() {
    return {
      fetchingStringReactant: "",
      fetchingStringProduct: ""
    };
  },
  methods: {
    /** To update the fetching datas when the Compound Components have changed */
    updateExpression(name, data) {
      if (name == "reactant") {
        this.fetchingStringReactant = data;
      } else {
        this.fetchingStringProduct = data;
      }
    },
    /** Send balancing request to server */
    async doBalance() {
      let formDatas = new FormData();
      formDatas.append("reactants", this.fetchingStringReactant);
      formDatas.append("products", this.fetchingStringProduct);
      formDatas.append("from_domain", browser_domain);
      fetch(backendURL + "balance", {
        body: formDatas,
        method: "POST"
      })
        .then(response => response.text())
        .then(result => {
          result = result.replace(" fade ", "");
          this.$refs.equation_details.innerHTML = result;
        });
    }
  }
};
</script>

<style scoped>
</style>
