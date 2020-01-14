<template>
  <div>
    <Tabs type="card" :value="currentTab" closable @on-click="handleTabClick">
      <TabPane :closable="false" name="requset" label="本组发起">
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
      <TabPane :closable="false" name="handler" label="本组处理">
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
      title="请求上报"
      footer-hide
      width="50"
      @on-cancel="requestModalHide"
    >
      <div style="width:600px;margin:0 auto;">
        <Form ref="request" :model="requestForm" :label-width="100">
          <FormItem label="模板">
            <Select v-model="requestForm.templateId">
              <Option v-for="tem in allTemplates" :key="tem.id" :value="tem.id">{{tem.name}}</Option>
            </Select>
          </FormItem>
          <FormItem label="服务请求名称">
            <Input v-model="requestForm.name" placeholder="服务请求名称"></Input>
          </FormItem>
          <FormItem label="服务请求角色">
            <Select v-model="requestForm.roleId">
              <Option v-for="role in currentUserRoles" :key="role.name" :value="role.name">{{role.displayName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="环境类型">
            <Select v-model="requestForm.envType">
              <Option value="test">测试</Option>
              <Option value="preProduction">准生产</Option>
              <Option value="production">生产</Option>
            </Select>
          </FormItem>
          <FormItem label="紧急程度">
            <Select v-model="requestForm.emergency">
              <Option value="normal">一般</Option>
              <Option value="urgent">紧急</Option>
            </Select>
          </FormItem>
          <FormItem label="描述">
            <Input type="textarea" v-model="requestForm.description" placeholder="描述"></Input>
          </FormItem>
          <FormItem label="请求附件">
            <Upload :on-success="uploadSuccess" ref="upload" action="/service-mgmt/v1/service-requests/attach-file">
                <Button icon="ios-cloud-upload-outline">上传附件</Button>
            </Upload>
          </FormItem>
          <FormItem> 
            <Button type="primary" @click="requestSubmit">提交</Button>
            <Button style="margin-left: 8px" @click="requestCancel">取消</Button>
          </FormItem>
        </Form>
      </div>
    </Modal>
    <Modal
      v-model="handlerModalVisible"
      title="任务处理"
      footer-hide
      width="50"
      @on-cancel="handlerModalHide"
    >
      <div style="width:600px;margin:0 auto;">
        <Form ref="request" :model="handlerForm" :label-width="100">
          <FormItem label="处理结果">
            <Select v-model="handlerForm.result">
              <Option value="Failed">处理失败/拒绝</Option>
              <Option value="Successful">处理完成/通过</Option>
            </Select>
          </FormItem>
          <FormItem label="描述">
            <Input v-model="handlerForm.resultMessage" placeholder="描述"></Input>
          </FormItem>
          <FormItem> 
            <Button type="primary" @click="handlerSubmit">提交</Button>
            <Button style="margin-left: 8px" @click="handlerCancel">取消</Button>
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
          title: "服务请求名称",
          key: "name",
          inputKey: "name",
          component: "Input",
          inputType: "text"
        },
        {
          title: "状态",
          key: "status",
          inputKey: "status",
          component: "PluginSelect",
          inputType: "select",
          options: [
            {
              value: "Summitted",
              label: "已提交"
            },
            {
              value: "Processing",
              label: "处理中"
            },
            {
              value: "Done",
              label: "完成"
            }
          ],
        },
        {
          title: "上报人",
          key: "reporter",
          inputKey: "reporter",
          component: "Input",
          inputType: "text"
        },
        {
          title: "上报时间",
          key: "reportTime",
          inputKey: "reportTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date"
        },
        {
          title: "环境类型",
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
          title: "紧急程度",
          key: "emergency",
          inputKey: "emergency",
          component: "PluginSelect",
          options: [
            {
              value: "normal",
              label: "一般"
            },
            {
              value: "urgent",
              label: "紧急"
            }
          ],
          inputType: "select"
        },
        {
          title: "描述",
          key: "description",
          inputKey: "description",
          component: "Input",
          inputType: "text"
        },
        {
          title: "操作",
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
                  附件下载
                </Button>}
              </div>
            );
          }
        }
      ],
      requestTableData: [],
      handlerColumns: [
        {
          title: "服务请求ID",
          key: "serviceRequestId",
          inputKey: "serviceRequestId",
          component: "Input",
          isNotFilterable: true
        },
        {
          title: "任务名称",
          key: "name",
          inputKey: "name",
          component: "Input",
          inputType: "text"
        },
        {
          title: "状态",
          key: "status",
          inputKey: "status",
          component: "PluginSelect",
          inputType: "select",
          options: [
            {
              value: "Pending",
              label: "待领取"
            },
            {
              value: "Processing",
              label: "处理中"
            },
            {
              value: "Successful",
              label: "处理成功"
            },
            {
              value: "Failed",
              label: "处理失败"
            }
          ],
        },
        {
          title: "上报人",
          key: "reporter",
          inputKey: "reporter",
          component: "Input",
          inputType: "text"
        },
        {
          title: "上报时间",
          key: "reportTime",
          inputKey: "reportTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date"
        },
        {
          title: "处理人",
          key: "operator",
          inputKey: "operator",
          component: "Input",
          inputType: "text"
        },
        {
          title: "处理时间",
          key: "operateTime",
          inputKey: "operateTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date"
        },
        {
          title: "描述",
          key: "description",
          inputKey: "description",
          component: "Input",
          inputType: "text"
        },
        {
          title: "操作",
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
                      领取
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
                      处理
                    </Button>
                  </div>
                );
                break;
              case "Successful":
                return (
                  <div></div>
                );
                break;
              case "Failed":
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
          label: "新增",
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
