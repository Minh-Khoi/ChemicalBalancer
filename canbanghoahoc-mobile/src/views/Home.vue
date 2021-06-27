<template>
  <span class="home">
    <ion-header :translucent="true">
      <ion-toolbar>
        <ion-title>Chemical equations balancing</ion-title>
      </ion-toolbar>
    </ion-header>
    
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Chemical equations balancing</ion-title>
        </ion-toolbar>
      </ion-header>
    </ion-content>

    <span id="container">
      <expression expressionName="reactant"></expression>
      <br><br><br>
      <expression expressionName="product"></expression>
    </span>
    <ion-button @click="doBalance()">Make the equation balanced</ion-button>
    <span class="equation_info">

    </span>
  </span>
</template>

<script lang="ts">
import ModalEquationVue from '@/components/ModalEquation.vue';
import { IonContent, IonHeader, IonTitle, IonToolbar,IonButton,IonItem, modalController} from '@ionic/vue';
import { defineComponent } from 'vue';
import Expression from "../components/Expression.vue";
import {backendURL, device_url} from '../router/server_route';
import ModalEquation from '../components/ModalEquation.vue';

export default defineComponent({
  name: 'Home',
  data() {
    return {
      fetchingStringReactant: "",
      fetchingStringProduct: ""
    };
  },
  components: {
    IonContent,
    IonHeader,
    IonButton,
    IonTitle,
    IonToolbar,IonItem,
    Expression
  },
  methods:{
    /** To update the fetching datas when the Compound Components have changed */
    updateExpression(name: string, data: string) {
      // console.log(data + " ------------ "+name);
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
      formDatas.append("from_domain", device_url);
      fetch(backendURL + "balance", {
        body: formDatas,
        method: "POST"
      })
        .then(response => response.text())
        .then(async function (result) {
          result = result.replace(" fade ", "");
          // document.querySelector(".home .equation_info").innerHTML = result;
          const new_mdl = await modalController.create({
            component: ModalEquation,
            cssClass: 'modal_compound_controller',
            componentProps: {
              // title: this.details,
              equationInfo : result,
            },
            swipeToClose: true,
          });
          new_mdl.present();
        });
    }
  

  }
});
</script>

<style scoped>
#container {
  text-align: center;
  position: absolute;
  left: 0;
  right: 0;
  top: 30%;
  transform: translateY(-50%);
}

#container strong {
  font-size: 20px;
  line-height: 26px;
}

#container p {
  font-size: 16px;
  line-height: 22px;
  
  color: #8c8c8c;
  
  margin: 0;
}

#container a {
  text-decoration: none;
}

ion-button{
  position: absolute;
  left: 0;
  right: 0;
  bottom:  30%;
  
}
</style>