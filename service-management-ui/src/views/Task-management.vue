<template>
  <div>
    <Tabs type="card" :value="currentTab" closable @on-click="handleTabClick">
      <TabPane :closable="false" name="requset" :label="$t('initiated_by_the_same_group')">
        <PluginTable
          :tableColumns="requestColumns"
          :tableData="requestTableData"
          :tableOuterActions="tableOuterActions"
          :pagination="requestPagination"
          @actionFun="actionFun"
          @handleSubmit="handleSubmit"
          @pageChange="requestPageChange"
          @pageSizeChange="requestPageSizeChange"
        />
      </TabPane>
      <TabPane :closable="false" name="handler" :label="$t('same_group_processing')">
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
      </TabPane>
    </Tabs>
    <Modal
      v-model="requestModalVisible"
      :title="$t('request_to_report')"
      footer-hide
      width="50"
      @on-cancel="requestModalHide"
    >
      <div style="width:600px;margin:0 auto;">
        <Form ref="request" :model="requestForm" :label-width="100">
          <FormItem :label="$t('template')">
            <Select v-model="requestForm.templateId">
              <Option v-for="tem in allTemplates" :key="tem.id" :value="tem.id">{{tem.name}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('service_request_name')">
            <Input v-model="requestForm.name" :placeholder="$t('service_request_name')"></Input>
          </FormItem>
          <FormItem :label="$t('service_request_role')">
            <Select v-model="requestForm.roleId">
              <Option v-for="role in currentUserRoles" :key="role.name" :value="role.name">{{role.displayName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="$t('environment_type')">
            <Select v-model="requestForm.envType">
              <Option value="test">测试</Option>
              <Option value="preProduction">准生产</Option>
              <Option value="production">生产</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('emergency_level')">
            <Select v-model="requestForm.emergency">
              <Option value="normal">{{$t('not_urgent')}}</Option>
              <Option value="urgent">{{$t('emergency')}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('describe')">
            <Input type="textarea" v-model="requestForm.description" :placeholder="$t('describe')"></Input>
          </FormItem>
          <FormItem :label="$t('reqest_attachment')">
            <Upload :on-success="uploadSuccess" ref="upload" action="/service-mgmt/v1/service-requests/attach-file">
                <Button icon="ios-cloud-upload-outline">{{$t('upload_attachment')}}</Button>
            </Upload>
          </FormItem>
          <FormItem> 
            <Button type="primary" @click="requestSubmit">{{$t('submit')}}</Button>
            <Button style="margin-left: 8px" @click="requestCancel">{{$t('cancle')}}</Button>
          </FormItem>
        </Form>
      </div>
    </Modal>
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
              <Option value="Failed/Rejected">{{$t('fail_or_reject')}}</Option>
              <Option value="Successful/Approved">{{$t('success_or_approve')}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('describe')">
            <Input v-model="handlerForm.resultMessage" :placeholder="$t('describe')"></Input>
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
import PluginTable from "../components/table";
import {
  queryServiceRequest,
  getAllServiceRequest,
  createServiceRequest,
  updateServiceRequest,
  getAllAvailableServiceTemplate,
  taskProcess,
  queryTask,
  taskTakeover,
  getCurrentUserRoles
} from "../api/server";

export default {
  name: "home",
  components: {
    PluginTable
  },
  data() {
    return {
      currentUserRoles:[],
      allTemplates:[],
      requestForm: {
        name: "",
        emergency: "",
        description: "",
        attachFileId: null,
        templateId:'',
        roleId:'',
      },
      handlerForm: {
        result: '',
        resultMessage:'',
        taskId: 0
      },
      handlerModalVisible:false,
      requestModalVisible: false,
      currentTab: "requset",
      requestColumns: [
        {
          title: this.$t('service_request_name'),
          key: "name",
          inputKey: "name",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t('status'),
          key: "status",
          inputKey: "status",
          component: "PluginSelect",
          inputType: "select",
          options: [
            {
              value: "Summitted",
              label: this.$t('summitted'),
            },
            {
              value: "Processing",
              label: this.$t('processing')
            },
            {
              value: "Done",
              label: this.$t('done')
            }
          ],
        },
        {
          title: this.$t('reporter'),
          key: "reporter",
          inputKey: "reporter",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t('reporting_time'),
          key: "reportTime",
          inputKey: "reportTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date"
        },
        {
          title: this.$t('environment_type'),
          key: "envType",
          inputKey: "envType",
          component: "PluginSelect",
          options: [
            {
              value: "test",
              label: "测试"
            },
            {
              value: "preProduction",
              label: "准生产"
            },
            {
              value: "production",
              label: "生产"
            }
          ],
          inputType: "select"
        },
        {
          title: this.$t('emergency_level'),
          key: "emergency",
          inputKey: "emergency",
          component: "PluginSelect",
          options: [
            {
              value: "normal",
              label: this.$t('not_urgent')
            },
            {
              value: "urgent",
              label: this.$t('emergency')
            }
          ],
          inputType: "select"
        },
        {
          title: this.$t('describe'),
          key: "description",
          inputKey: "description",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t('action'),
          key: "action",
          width: 150,
          align: "center",
          isNotFilterable: true,
          render: (h, params) => {
            return (
              <div>
                {params.row.attachFile && <Button
                  type="primary"
                  size="small"
                  onClick={() => this.downloadFile(params.row.id)}
                >
                  {this.$t('download_attachment')}
                </Button>}
              </div>
            );
          }
        }
      ],
      requestTableData: [],
      handlerColumns: [
        {
          title: this.$t('service_request_ID'),
          key: "serviceRequestId",
          inputKey: "serviceRequestId",
          component: "Input",
          isNotFilterable: true
        },
        {
          title: this.$t('task_name'),
          key: "name",
          inputKey: "name",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t('status'),
          key: "status",
          inputKey: "status",
          component: "PluginSelect",
          inputType: "select",
          options: [
            {
              value: "Pending",
              label: this.$t('pending')
            },
            {
              value: "Processing",
              label: this.$t('processing')
            },
            {
              value: "Successful/Approved",
              label: this.$t('success_or_approve')
            },
            {
              value: "Failed/Rejected",
              label: this.$t('fail_or_reject')
            }
          ],
        },
        {
          title: this.$t('reporter'),
          key: "reporter",
          inputKey: "reporter",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t('reporting_time'),
          key: "reportTime",
          inputKey: "reportTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date"
        },
        {
          title: this.$t('operator'),
          key: "operator",
          inputKey: "operator",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t('operate_time'),
          key: "operateTime",
          inputKey: "operateTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date"
        },
        {
          title: this.$t('describe'),
          key: "description",
          inputKey: "description",
          component: "Input",
          inputType: "text"
        },
        {
          title: this.$t('action'),
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
                      {this.$t('receive')}
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
                      onClick={() => {this.handlerForm.taskId = params.row.id; this.handlerModalVisible = true}}
                    >
                      {this.$t('deal_with')}
                    </Button>
                  </div>
                );
                break;
              case "Successful/Approved":
                return (
                  <div></div>
                );
                break;
              case "Failed/Rejected":
                return (
                  <div></div>
                );
                break;
            }
            
          }
        }
      ],
      handlerTableData: [],
      tableOuterActions: [
        {
          label: this.$t('add'),
          props: {
            type: "success",
            icon: "md-add",
            disabled: false
          },
          actionType: "add"
        }
      ],
      requestPayload: {
        filters: [],
        pageable: {
          pageSize: 10,
          startIndex: 0
        },
        paging: true
      },
      handlerPayload: {
        filters: [],
        pageable: {
          pageSize: 10,
          startIndex: 0
        },
        paging: true
      },
      requestPagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      handlerPagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      }
    };
  },
  methods: {
    downloadFile(id) {
      let a = document.createElement("a");
      const body = document.body;
      a.setAttribute("href", `/service-mgmt/v1/service-requests/${id}/attach-file`);
      a.setAttribute("id", "downloadFile");
      body.appendChild(a);
      a.click();
      body.removeChild(document.getElementById("downloadFile"));
    },
    uploadSuccess(res, file, fileList) {
      this.requestForm.attachFileId = res.data
    },
    requestModalHide() {
      this.requestModalVisible = false;
    },
    handlerModalHide() {
      this.handlerModalVisible = false;
    },
    requestCancel() {
      this.requestModalVisible = false;
      this.requestForm.name = ''
      this.requestForm.emergency = ''
      this.requestForm.description = ''
      this.requestForm.templateId = ''
      this.requestForm.roleId = ''
    },
    async requestSubmit() {
      const {status} = await createServiceRequest(this.requestForm)
      if(status === 'OK') {
        this.requestCancel()
        this.getData();
        this.requestForm.attachFileId = null
        this.$refs.upload.clearFiles()
      }
    },
    handlerCancel() {
      this.handlerModalVisible = false;
      this.handlerForm.result = ''
      this.handlerForm.resultMessage = ''
    },
    async handlerSubmit() {
      const {status} = await taskProcess(this.handlerForm)
      if(status === 'OK') {
        this.handlerCancel()
        this.getProcessData();
      }
    },
    actionFun(type, data) {
      switch (type) {
        case "add":
          this.requestModalVisible = true;
          break;
      }
    },
    requestPageChange(current) {
      this.requestPagination.currentPage = current;
      this.getData();
    },
    requestPageSizeChange(size) {
      this.requestPagination.pageSize = size;
      this.getData();
    },
    handlerPageChange(current) {
      this.handlerPagination.currentPage = current;
      this.getProcessData();
    },
    handlerPageSizeChange(size) {
      this.handlerPagination.pageSize = size;
      this.getProcessData();
    },
    handleSubmit(filters) {
      this.requestPayload.filters = filters;
      this.getData();
    },
    handleSubmitForprocess(filters) {
      this.handlerPayload.filters = filters;
      this.getProcessData();
    },
    handleTabClick(tab) {
      if(tab === 'requset'){
        this.getData();
      }else{
        this.getProcessData();
      }
    },
    async getProcessData() {
      this.handlerPayload.pageable.pageSize = this.handlerPagination.pageSize;
      this.handlerPayload.pageable.startIndex =
        this.handlerPagination.pageSize *
        (this.handlerPagination.currentPage - 1);
      const { status, message, data } = await queryTask(
        this.handlerPayload
      );
      if (status === "OK") {
        this.handlerTableData = data.contents;
        this.handlerPagination.total = data.pageInfo.totalRows;
      }
    },
    async getData() {
      this.requestPayload.pageable.pageSize = this.requestPagination.pageSize;
      this.requestPayload.pageable.startIndex =
        this.requestPagination.pageSize *
        (this.requestPagination.currentPage - 1);
      const { status, message, data } = await queryServiceRequest(
        this.requestPayload
      );
      if (status === "OK") {
        this.requestTableData = data.contents;
        this.requestPagination.total = data.pageInfo.totalRows;
      }
    },
    async taskTakeOver(id) {
      await taskTakeover({taskId:id});
      this.getProcessData();
    },
    async getTemplates() {
      const {data} = await getAllAvailableServiceTemplate()
      this.allTemplates = data
    },
    async getRolesByCurrentUser() {
      const {data} = await getCurrentUserRoles()
      this.currentUserRoles = data
    },
  },
  async mounted() {
    this.getData();
    this.getTemplates();
    this.getRolesByCurrentUser()
  }
};
</script>
