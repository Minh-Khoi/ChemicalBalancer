<template>
  <span class="compound">
    <span
      :class="'btn ' + ((isValidCompound) ? 'btn-info' : 'btn-warning')"
      v-html="detailsReformatted"
      @click="showInfo"
      :title="(!isValidCompound) ? compoundInfo : ''"
    ></span>
    <span class="btn btn-danger" @click="selfDelete">X</span>

    <!-- Modal -->
    <div class="modal fade" :id="'myModal_'+details" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title">{{details}}</h4>
          </div>
          <div class="modal-body">
            <h3 class="text-danger font-weight-bold">{{compoundInfo.formula}}</h3>
            <p>Vietnamese Name: {{compoundInfo.vietnamese_name}}</p>
            <p v-for="info in compoundInfo.moreInfo" :key="info.id">
              {{ info }}
              <br />
            </p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <!-- End Modal -->
  </span>
</template>

<script>
import { backendURL } from "../router/server_route";

export default {
  name: "compound",
  props: ["details"],
  data() {
    return {
      isValidCompound: true,
      compoundInfo: ""
    };
  },
  computed: {
    detailsReformatted() {
      let result = "";
      for (let char of this.details.split("")) {
        let newChar = isNaN(char) ? char : "<sub>" + char + "</sub>";
        result += newChar;
      }
      return result;
    }
  },
  methods: {
    selfDelete() {
      this.$parent.deleteCompound(this.details);
    },

    /**
     * Event handler when user click on the <span> which is showing Compound 's formula
     */
    showInfo() {
      // console.log(this.isValidCompound);
      if (this.isValidCompound) {
        window.$("#myModal_" + this.details).modal();
      }
    }
  },
  async mounted() {
    let formDatas = new FormData();
    formDatas.append("compoundFormula", this.details);
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

<style scope>
/* span:hover::after {
  visibility: visible;
  color: red;
} */
</style>