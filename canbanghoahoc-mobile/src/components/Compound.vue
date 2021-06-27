<template>
  <span class="compound">
    <ion-button v-html="detailsReformatted" @click="showInfo" 
      :color="(!isValidCompound || !compoundInfo ) ? 'warning' : 'success' "></ion-button>
    <ion-button @click="selfDelete" color="danger">X</ion-button>
    <!-- <span>FUCK you Bitch</span> -->
  </span>
</template>

<script>
import {backendURL, deviceUrl} from '../router/server_route.ts';
import ModalCompound from './ModalCompound.vue';
import { IonButton, modalController } from '@ionic/vue';

export default {
  name: "compound",
  components:{IonButton},
  props: ["details"],
  data() {
    return {
      isValidCompound: true,
      compoundInfo: "",
      modal: null
    };
  },
  computed: {
    detailsReformatted() {
      let result = "";
      for (const char of this.details.split("")) {
        const newChar = isNaN(char) ? char : "<sub>" + char + "</sub>";
        result += newChar;
      }
      return result;
    }
  },
  methods: {
    /** When The "X" button was click, the "Compound" instance will delete itself */
    selfDelete() {
      console.log(this.$parent);
      this.$parent.deleteCompound(this.details);
    },

    /**
     * Event handler when user click on the <ion-button> which is showing Compound 's formula
     */
    async showInfo() {
      const modal = await modalController.create({
          component: ModalCompound,
          cssClass: 'modal_compound_controller',
          componentProps: {
            title: this.details,
            compoundDetails : this.compoundInfo,
          },
          swipeToClose: true,
      })
        return modal.present()
    },

    // /** Delete the modal of ModalCompound instance info obove */
    // dismissInfo(){
    //   console.log("dismiss function invoked");
    //   this.modal.dismiss();
    // }
  },
  async mounted() {
    const formDatas = new FormData();
    formDatas.append("compoundFormula", this.details);
    formDatas.append("from_domain", deviceUrl);
    fetch(backendURL + "checkcompound", {
      body: formDatas,
      method: "POST"
    })
      .then(response => response.text())
      .then(result => {
        // console.log(result);
        if (
          result.indexOf("does not stand for any compound or subtance") != -1
        ) {
          this.isValidCompound = false;
        }
        this.compoundInfo = JSON.parse(result);
      });
  }
};
</script>

<style scoped>
* {
  visibility: visible;
  color: black;
}
</style>