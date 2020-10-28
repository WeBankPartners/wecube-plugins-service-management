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
      @sortHandler="sortHandler"
      @pageSizeChange="handlerPageSizeChange"
    />
    <Modal
      v-model="handlerModalVisible"
      :title="$t('task_processing')"
      footer-hide
      width="70"
      @on-cancel="handlerModalHide"
    >
      <div style="background:rgb(205 206 206);margin-bottom: -1px;font-weight: 600;border-radius: 3px 3px 0 0;" v-if="processData.requestId">{{$t('service_request_data')}}</div>
      <Card v-if="processData.requestId">
        <Row>
          <Col style="margin-bottom:10px" span="6">
            <div class="process-title">
              <strong>
                {{$t("service_request_name")}}
              </strong>
            </div>
            <div class="process-value">
              {{processData.requestName}}
            </div>
          </Col>
          <Col style="margin-bottom:10px" span="6">
            <div class="process-title">
              <strong>
                {{$t("reporter")}}
              </strong>
            </div>
            <div class="process-value">
              {{processData.reporter}}
            </div>
          </Col>
          <Col style="margin-bottom:10px" span="6">
            <div class="process-title">
              <strong>
                {{$t("reporting_time")}}
              </strong>
            </div>
            <div class="process-value">
              {{processData.reportTime}}
            </div>
          </Col>
          <Col style="margin-bottom:10px" span="6">
            <div class="process-title">
              <strong>
                {{$t("status")}}
              </strong>
            </div>
            <div class="process-value">
              {{processData.status}}
            </div>
          </Col>
          <Col span="6">
            <div class="process-title">
              <strong>
                {{$t("environment_type")}}
              </strong>
            </div>
            <div class="process-value">
              {{processData.envType}}
            </div>
          </Col>
          <Col span="6">
            <div class="process-title">
              <strong>
                {{$t("emergency_level")}}
              </strong>
            </div>
            <div class="process-value">
              {{emergency[processData.emergency]}}
            </div>
          </Col>
          <Col span="6">
            <div class="process-title">
              <strong>
                {{$t("reqest_attachment")}}
              </strong>
            </div>
            <div class="process-value">
              <a target="_blank" :href="processData.attachFile ? processData.attachFile.fileUrl : ''">{{processData.attachFile ? processData.attachFile.fileName : ''}}</a>
            </div>
          </Col>
          <Col span="6">
            <div class="process-title">
              <strong>
                {{$t("describe")}}
              </strong>
            </div>
            <div class="process-value">
              {{processData.description}}
            </div>
          </Col>
        </Row>
      </Card>
      <br v-if="processData.requestId" />
      <List v-if="processData.requestId && processData.otherTasks.length > 0" border>
        <ListItem v-for="task in processData.otherTasks" :key="task.taskId">
          <span style="margin-right: 25px">
            <strong>
              {{$t("task_name")}}:
            </strong>
            {{task.taskName}}
          </span>
          <span style="margin-right: 25px">
            <strong>
              {{$t("operator")}}:
            </strong>
            {{task.operator}}
          </span>
          <span style="margin-right: 25px">
            <strong>
              {{$t("status")}}:
            </strong>
            {{task.status}}
          </span>
          <span style="margin-right: 25px">
            <strong>
              {{$t("process_result")}}:
            </strong>
            {{task.result}}
          </span>
          <span style="margin-right: 25px">
            <strong>
              {{$t("operate_time")}}:
            </strong>
            {{task.operateTime}}
          </span>
          <span style="margin-right: 25px">
            <strong>
              {{$t("describe")}}:
            </strong>
            {{task.resultMessage}}
          </span>
        </ListItem>
      </List>
      <br v-if="processData.requestId && processData.otherTasks.length > 0" />
      <div style="background:rgb(190 217 233);margin-bottom: -1px;font-weight: 600;border-radius: 3px 3px 0 0;">{{$t('task_data')}}</div>
      <Card>
        <Row>
          <Col style="margin-bottom:10px" span="6">
            <div class="process-title">
              <strong>
                {{$t("task_name")}}
              </strong>
            </div>
            <div class="process-value">
              {{taskData.name}}
            </div>
          </Col>
          <Col style="margin-bottom:10px" span="6">
            <div class="process-title">
              <strong>
                {{$t("status")}}
              </strong>
            </div>
            <div class="process-value">
              {{taskData.status}}
            </div>
          </Col>
          <Col style="margin-bottom:10px" span="6">
            <div class="process-title">
              <strong>
                {{$t("reporter")}}
              </strong>
            </div>
            <div class="process-value">
              {{taskData.reporter}}
            </div>
          </Col>
          <Col style="margin-bottom:10px" span="6">
            <div class="process-title">
              <strong>
                {{$t("reporting_time")}}
              </strong>
            </div>
            <div class="process-value">
              {{taskData.reportTime}}
            </div>
          </Col>
          <Col span="6">
            <div class="process-title">
              <strong>
                {{$t("operator")}}
              </strong>
            </div>
            <div class="process-value">
              {{taskData.operator}}
            </div>
          </Col>
          <Col span="6">
            <div class="process-title">
              <strong>
                {{$t("over_time")}}
              </strong>
            </div>
            <div class="process-value">
              {{taskData.overTime}}
            </div>
          </Col>
          <Col span="12">
            <div class="process-title">
              <strong>
                {{$t("describe")}}
              </strong>
            </div>
            <div class="process-value">
              {{taskData.description}}
            </div>
          </Col>
        </Row>
      </Card>
      <br />
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
  taskTakeover,
  getPreprocessDataByTaskId
} from "../api/server";
export default {
  components: {
    PluginTable
  },
  data() {
    return {
      emergency: {
        normal: this.$t("not_urgent"),
        urgent: this.$t("emergency")
      },
      taskData: {},
      handlerForm: {
        result: "",
        resultMessage: "",
        taskId: 0
      },
      processData: {},
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
          isNotFilterable: true,
          sortable: 'custom'
        },
        {
          title: this.$t("task_name"),
          key: "name",
          inputKey: "name",
          component: "Input",
          inputType: "text",
          sortable: 'custom',
          render: (h, params) => {
            let max_chars = 50
            let content = params.row.name || ''
            let cut_content = content
            let bytes_length = content.replace(/[^\x00-\xff]/g, '**').length
            if (bytes_length >= max_chars){
              let chinese_count = content.match(/[^\x00-\xff]/g) ? content.match(/[^\x00-\xff]/g).length : 0
              cut_content = content.substring(0, max_chars - Math.min(Math.ceil(chinese_count/2), max_chars/2)) + '...'
            }
            return (
              <span title={content}>{cut_content}</span>
            )
          }
        },
        {
          title: this.$t("status"),
          key: "status",
          inputKey: "status",
          component: "PluginSelect",
          inputType: "select",
          sortable: 'custom',
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
          sortable: 'custom',
          render: (h, params) => {
            return (
              <div class="reporter" style={`background: ${this.colorHash.hex(params.row.reporter)};`}>{params.row.reporter}</div>
            )
          }
        },
        {
          title: this.$t("reporting_time"),
          key: "reportTime",
          inputKey: "reportTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date",
          sortable: 'custom'
        },
        {
          title: this.$t("operator"),
          key: "operator",
          inputKey: "operator",
          component: "Input",
          inputType: "text",
          sortable: 'custom'
        },
        {
          title: this.$t("operate_time"),
          key: "operateTime",
          inputKey: "operateTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date",
          sortable: 'custom'
        },
        {
          title: this.$t("over_time"),
          key: "overTime",
          inputKey: "overTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date",
          className: "reporter-container",
          sortable: 'custom',
          render: (h, params) => {
            return (
              <div class="reporter" style={`background: ${params.row.status === "Processing" || params.row.status === "Pending" ? this.getColor(params.row.reportTime, params.row.overTime) : '#fff'};`}>{params.row.overTime}</div>
            )
          }
        },
        {
          title: this.$t("describe"),
          key: "description",
          inputKey: "description",
          component: "Input",
          inputType: "text",
          sortable: 'custom',
          render: (h, params) => {
            let max_chars = 50
            let content = params.row.description || ''
            let cut_content = content
            let bytes_length = content.replace(/[^\x00-\xff]/g, '**').length
            if (bytes_length >= max_chars){
              let chinese_count = content.match(/[^\x00-\xff]/g) ? content.match(/[^\x00-\xff]/g).length : 0
              cut_content = content.substring(0, max_chars - Math.min(Math.ceil(chinese_count/2), max_chars/2)) + '...'
            }
            return (
              <span title={content}>{cut_content}</span>
            )
          }
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
                        this.taskData = params.row
                        this.nextActions = params.row.allowedOptions;
                        this.handlerModalVisible = true;
                        this.getPreprocessDataByTaskId()
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
    async getPreprocessDataByTaskId () {
      this.processData = {}
      const { data, status } = await getPreprocessDataByTaskId(this.handlerForm.taskId)
      if(status === 'OK') {
        this.processData = data
      }
    },
     getColor(reportTime,overTime) {
      if (!reportTime || !overTime) {
        return '#fff'
      }
      const report = (new Date(reportTime.replace(new RegExp("-","gm"),"/"))).getTime()
      const over = (new Date(overTime.replace(new RegExp("-","gm"),"/"))).getTime()
      const current = new Date().getTime()
      const step = (over - report) / 3
      if (over < current) {
        return '#ed4014'
      }
      if (current < (report + step)) {
        return '#fff'
      }
      if ((report + step) < current && current < (report + step * 2)) {
        return '#2db7f5'
      }
      if ((report + step + step) < current && current < over) {
        return '#ff9900'
      }
    },
    sortHandler (data) {
      if (data.order === 'normal') {
        delete this.handlerPayload.sorting
      } else {
        this.handlerPayload.sorting = {
          asc: data.order === 'asc',
          field: data.key
        }
      }
      this.getProcessData()
    },
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
  // background-color: rgb(126, 196, 240);
}
.reporter-container {
  .ivu-table-cell {
    height: 100%;
    width: 100%;
    padding: 0;
    .ivu-table-cell-sort {
      margin-left: 18px;
    }
  }
}
</style>
