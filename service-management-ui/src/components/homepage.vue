<template>
  <div>
    <PluginTable
      :tableColumns="handlerColumns"
      :tableData="handlerTableData"
      :tableOuterActions="[]"
      :pagination="handlerPagination"
      @actionFun="actionFun"
      @handleSubmit="handleSubmitForprocess"
      @pageChange="handlerPageChange"
      @pageSizeChange="handlerPageSizeChange"
    />
    <Modal
      v-model="handlerModalVisible"
      :title="$t('task_processing')"
      footer-hide
      width="50"
      @on-cancel="handlerModalHide"
    >
      <div style="width:600px;margin:0 auto;">
        <Form ref="request" :model="handlerForm" :label-width="100">
          <FormItem :label="$t('process_result')">
            <Select v-model="handlerForm.result">
              <Option v-for="item in nextActions" :key="item" :value="item">{{item}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('describe')">
            <Input type="textarea" v-model="handlerForm.resultMessage" :placeholder="$t('describe')"></Input>
          </FormItem>
          <FormItem>
            <Button type="primary" @click="handlerSubmit">{{$t('submit')}}</Button>
            <Button style="margin-left: 8px" @click="handlerCancel">{{$t('cancle')}}</Button>
          </FormItem>
        </Form>
      </div>
    </Modal>
  </div>
</template>
<script>
import PluginTable from "./table";
import ColorHash from "../util/hash.js"
import {
  taskProcess,
  queryMyTask,
  taskTakeover
} from "../api/server";
export default {
  components: {
    PluginTable
  },
  data() {
    return {
      handlerForm: {
        result: "",
        resultMessage: "",
        taskId: 0
      },
      nextActions: [],
      handlerModalVisible: false,
      handlerPagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      handlerPayload: {
        filters: [],
        pageable: {
          pageSize: 10,
          startIndex: 0
        },
        paging: true
      },
      colorHash: new ColorHash(),
      handlerTableData: [],
      handlerColumns: [
        {
          title: this.$t("service_request_ID"),
          key: "serviceRequestId",
          inputKey: "serviceRequestId",
          component: "Input",
          isNotFilterable: true
        },
        {
          title: this.$t("task_name"),
          key: "name",
          inputKey: "name",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t("status"),
          key: "status",
          inputKey: "status",
          component: "PluginSelect",
          inputType: "select",
          options: [
            {
              value: "Pending",
              label: this.$t("pending")
            },
            {
              value: "Processing",
              label: this.$t("processing")
            },
            {
              value: "Successful/Approved",
              label: this.$t("success_or_approve")
            },
            {
              value: "Failed/Rejected",
              label: this.$t("fail_or_reject")
            }
          ]
        },
        {
          title: this.$t("reporter"),
          key: "reporter",
          inputKey: "reporter",
          component: "Input",
          inputType: "text",
          className: "reporter-container",
          render: (h, params) => {
            return (
              <div class="reporter" style={`background: ${this.colorHash.hex('workflow')};`}>{params.row.reporter}</div>
            )
          }
        },
        {
          title: this.$t("reporting_time"),
          key: "reportTime",
          inputKey: "reportTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date"
        },
        {
          title: this.$t("operator"),
          key: "operator",
          inputKey: "operator",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t("operate_time"),
          key: "operateTime",
          inputKey: "operateTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date"
        },
        {
          title: this.$t("describe"),
          key: "description",
          inputKey: "description",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t("action"),
          key: "action",
          width: 150,
          align: "center",
          isNotFilterable: true,
          render: (h, params) => {
            switch (params.row.status) {
              case "Pending":
                return (
                  <div>
                    <Button
                      type="primary"
                      size="small"
                      onClick={() => this.taskTakeOver(params.row.id)}
                    >
                      {this.$t("receive")}
                    </Button>
                  </div>
                );
                break;
              case "Processing":
                return (
                  <div>
                    <Button
                      type="primary"
                      size="small"
                      onClick={() => {
                        this.handlerForm.taskId = params.row.id;
                        this.nextActions = params.row.allowedOptions;
                        this.handlerModalVisible = true;
                      }}
                    >
                      {this.$t("deal_with")}
                    </Button>
                  </div>
                );
                break;
              case "Successful/Approved":
                return <div></div>;
                break;
              case "Failed/Rejected":
                return <div></div>;
                break;
            }
          }
        }
      ],
    }
  },
  mounted() {
    
    this.getProcessData()
  },
  methods: {
    actionFun(type, data) {
      switch (type) {
        case "add":
          break;
      }
    },
    async handlerSubmit() {
      const { status } = await taskProcess(this.handlerForm);
      if (status === "OK") {
        this.handlerCancel();
        this.getProcessData();
      }
    },
    handlerModalHide() {
      this.handlerModalVisible = false;
      this.nextActions = []
    },
     handlerCancel() {
      this.handlerModalVisible = false;
      this.handlerForm.result = "";
      this.handlerForm.resultMessage = "";
      this.nextActions = []
    },
    handlerPageChange(current) {
      this.handlerPagination.currentPage = current;
      this.getProcessData();
    },
    handlerPageSizeChange(size) {
      this.handlerPagination.pageSize = size;
      this.getProcessData();
    },
    async getProcessData() {
      this.handlerPayload.pageable.pageSize = this.handlerPagination.pageSize;
      this.handlerPayload.pageable.startIndex =
        this.handlerPagination.pageSize *
        (this.handlerPagination.currentPage - 1);
      const { status, message, data } = await queryMyTask(
        this.handlerPayload
      );
      if (status === "OK") {
        this.handlerTableData = data.contents
        this.handlerPagination.total = data.pageInfo.totalRows;
      }
    },
    async taskTakeOver(id) {
      await taskTakeover({ taskId: id });
      this.getProcessData();
    },
    handleSubmitForprocess(filters) {
      this.handlerPayload.filters = filters;
      this.getProcessData();
    },

  }
}
</script>
<style lang="scss">
.reporter {
  height: 100%;
  width: 100%;
  padding-top: 10px;
  padding-left: 18px;
  padding-right: 18px;
}
.reporter-container {
  .ivu-table-cell {
    height: 100%;
    width: 100%;
    padding: 0;
  }
}
</style>
