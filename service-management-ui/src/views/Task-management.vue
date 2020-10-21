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
          @sortHandler="sortHandler"
          @pageChange="requestPageChange"
          @pageSizeChange="requestPageSizeChange"
        />
      </TabPane>
      <TabPane :closable="false" name="handler" :label="$t('same_group_processing')">
        <HomePage/>
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
        <Form ref="requestForm" :rules="ruleValidate" :model="requestForm" :label-width="110">
          <FormItem :label="$t('template')">
            <Select @on-open-change="getTemplates" @on-change="templateChanged" v-model="requestForm.templateId">
              <Option v-for="tem in allTemplates" :key="tem.id" :value="tem.id">{{tem.name}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('target_object')">
            <Select @on-open-change="getEntityDataByTemplateId" v-model="requestForm.rootDataId">
              <Option v-for="tem in entityData" :key="tem.guid" :value="tem.guid">{{tem.displayName}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('service_request_name')" prop="name">
            <Input v-model="requestForm.name" :placeholder="$t('service_request_name')"></Input>
          </FormItem>
          <FormItem :label="$t('service_request_role')">
            <Select @on-open-change="getRolesByCurrentUser" v-model="requestForm.roleName">
              <Option
                v-for="role in currentUserRoles"
                :key="role.name"
                :value="role.name"
              >{{role.displayName}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('environment_type')">
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
            <Upload
              :on-success="uploadSuccess"
              ref="upload"
              action="/service-mgmt/v1/service-requests/attach-file"
            >
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
  </div>
</template>

<script>
import PluginTable from "../components/table";
import HomePage from "../components/homepage"
import {
  queryServiceRequest,
  getAllServiceRequest,
  createServiceRequest,
  updateServiceRequest,
  getAllAvailableServiceTemplate,
  queryMyTask,
  getCurrentUserRoles,
  getEntityDataByTemplateId
} from "../api/server";

export default {
  name: "home",
  components: {
    PluginTable,
    HomePage
  },
  data() {
    return {
      ruleValidate: {
        name: [
          {
            required: true,
            message: "The name cannot be empty",
            trigger: "blur"
          }
        ]
      },
      currentUserRoles: [],
      allTemplates: [],
      entityData: [],
      requestForm: {
        name: "",
        emergency: "",
        description: "",
        attachFileId: null,
        templateId:'',
        roleName:'',
        rootDataId: ''
      },
      requestModalVisible: false,
      currentTab: "requset",
      requestColumns: [
        {
          title: this.$t("service_request_name"),
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
              value: "Summitted",
              label: this.$t("summitted")
            },
            {
              value: "Processing",
              label: this.$t("processing")
            },
            {
              value: "Done",
              label: this.$t("done")
            }
          ]
        },
        {
          title: this.$t("reporter"),
          key: "reporter",
          inputKey: "reporter",
          component: "Input",
          inputType: "text",
          sortable: 'custom',
        },
        {
          title: this.$t("reporting_time"),
          key: "reportTime",
          inputKey: "reportTime",
          component: "DatePicker",
          type: "datetimerange",
          inputType: "date",
          sortable: 'custom',
        },
        {
          title: this.$t("environment_type"),
          key: "envType",
          inputKey: "envType",
          component: "PluginSelect",
          sortable: 'custom',
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
          title: this.$t("emergency_level"),
          key: "emergency",
          inputKey: "emergency",
          component: "PluginSelect",
          sortable: 'custom',
          options: [
            {
              value: "normal",
              label: this.$t("not_urgent")
            },
            {
              value: "urgent",
              label: this.$t("emergency")
            }
          ],
          inputType: "select"
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
            return (
              <div>
                {params.row.attachFile && (
                  <Button
                    type="primary"
                    size="small"
                    onClick={() => this.downloadFile(params.row.id)}
                  >
                    {this.$t("download_attachment")}
                  </Button>
                )}
              </div>
            );
          }
        }
      ],
      requestTableData: [],
      handlerPayload: {
        filters: [],
        pageable: {
          pageSize: 10,
          startIndex: 0
        },
        paging: true
      },
      handlerPagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      tableOuterActions: [
        {
          label: this.$t("add"),
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
      
      requestPagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      
    };
  },
  methods: {
    downloadFile(id) {
      let a = document.createElement("a");
      const body = document.body;
      a.setAttribute(
        "href",
        `/service-mgmt/v1/service-requests/${id}/attach-file`
      );
      a.setAttribute("id", "downloadFile");
      body.appendChild(a);
      a.click();
      body.removeChild(document.getElementById("downloadFile"));
    },
    uploadSuccess(res, file, fileList) {
      this.requestForm.attachFileId = res.data;
    },
    requestModalHide() {
      this.requestModalVisible = false;
    },
    templateChanged (v) {
      this.entityData = []
      this.requestForm.rootDataId = ""
    },
    async getEntityDataByTemplateId (v) {
      if (v && this.requestForm.templateId && this.requestForm.templateId.length > 0) {
        const { data, status } = await getEntityDataByTemplateId(this.requestForm.templateId)
        this.entityData = []
        if (status === "OK") {
          this.entityData = data
        }
      }
    },
    requestCancel() {
      this.requestModalVisible = false;
      this.requestForm.name = "";
      this.requestForm.emergency = "";
      this.requestForm.description = "";
      this.requestForm.templateId = "";
      this.requestForm.roleId = "";
      this.requestForm.rootDataId = "";
    },
    requestSubmit() {
      this.$refs.requestForm.validate(async valid => {
        if (valid) {
          const { status } = await createServiceRequest(this.requestForm);
          if (status === "OK") {
            this.requestCancel();
            this.getData();
            this.requestForm.attachFileId = null;
            this.$refs.upload.clearFiles();
          }
        }
      });
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
    
    handleSubmit(filters) {
      this.requestPayload.filters = filters;
      this.getData();
    },
    
    handleTabClick(tab) {
      if (tab === "requset") {
        this.getData();
      } else {
        this.getProcessData();
      }
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
        this.handlerTableData = data.contents;
        this.handlerPagination.total = data.pageInfo.totalRows;
      }
    },
    sortHandler (data) {
      if (data.order === 'normal') {
        delete this.requestPayload.sorting
      } else {
        this.requestPayload.sorting = {
          asc: data.order === 'asc',
          field: data.key
        }
      }
      this.getData()
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
    async getTemplates() {
      const { data } = await getAllAvailableServiceTemplate();
      this.allTemplates = data;
    },
    async getRolesByCurrentUser() {
      const { data } = await getCurrentUserRoles();
      this.currentUserRoles = data;
    }
  },
  async mounted() {
    this.getData();
    this.getTemplates();
    this.getRolesByCurrentUser();
  }
};
</script>
