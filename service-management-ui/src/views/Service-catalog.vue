<template>
  <div>
    <Tabs type="card" :value="currentTab" closable @on-click="handleTabClick">
      <TabPane :closable="false" name="templateDefinition" :label="$t('template_definition')">
        <div style="margin-bottom:10px;">
          <Button type="success" @click="addTemplateHandler">{{$t('add')}}</Button>
        </div>
        <Table border :data="temData" :columns="temColumns"></Table>
        <Modal 
          v-model="addTemplateVisible"
          :title="$t('add')"
          footer-hide
          width="50"
          @on-cancel="addTemplateModalHide"
        >
          <Card style="width:600px;margin:0 auto;">
          <Row slot="title">
            <Col span="18">
              <p>{{$t('template_attribute')}}</p>
            </Col>
          </Row>
          <Collapse accordion>
            <Panel v-for="(item, index) in templateAttrs" :key="item.id" :name="index.toString()">
              <span>{{ item.name }}</span>
              <span class="serviceCatalog-margin-right">
                <Tooltip :content="$t('delete_attribute')" placement="top-start">
                  <Button
                    size="small"
                    type="error"
                    disabled
                    @click.stop.prevent="deleteAttr(item.id)"
                    icon="ios-trash"
                  ></Button>
                </Tooltip>
              </span>
              <div slot="content">
                <Form
                  class="validation-form"
                  :model="item"
                  label-position="left"
                  :label-width="100"
                >
                  <FormItem :label="$t('attribute_name')" prop="attrName">
                    <Input v-model="item.name" disabled></Input>
                  </FormItem>
                  <FormItem :label="$t('type')" prop="type">
                    <Input v-model="item.type" disabled></Input>
                  </FormItem>
                </Form>
              </div>
            </Panel>
          </Collapse>
          <br />
          <Button
            type="primary"
            size="small"
            long
            disabled
            ghost
            @click.stop.prevent="addAttr"
            icon="md-add"
          ></Button>
        </Card>
        <Form
          style="width:600px;margin:30px auto;"
          :model="form"
          label-position="left"
          :label-width="100"
        >
          <FormItem :label="$t('template_name')" prop="attrName">
            <Input v-model="form.name"></Input>
          </FormItem>
          <FormItem :label="$t('service_directory')" prop="attrName">
            <Select @on-open-change="getAllAvailableServiceCatalogues" v-model="form.serviceCatalogId" @on-change="serviceCatalogChangeHandler" >
              <Option
                v-for="item in serviceCatalogues"
                :key="item.id"
                :value="item.id"
              >{{item.name}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('service_channel')" prop="attrName">
            <Select v-model="form.servicePipelineId">
              <Option
                v-for="pipeline in servicePipeline"
                :key="pipeline.id"
                :value="pipeline.id"
              >{{pipeline.name}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('process')" prop="attrName">
            <Select @on-open-change="getAllProcessDefinitionKeys" @on-change="processChanged" v-model="form.procDefKey">
              <Option
                v-for="process in allProcessDefinitionKeys"
                :key="process.procDefId"
                :value="process.procDefKey"
              >{{process.procDefName}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('describe')" prop="attrName">
            <Input type="textarea" v-model="form.description"></Input>
          </FormItem>
        </Form>
        <div style="margin:30px auto;text-align:center">
          <Button type="primary" @click="createServiceRequestTemplate">{{$t('submit')}}</Button>
        </div>
        </Modal>
        
      </TabPane>
      <TabPane :closable="false" name="serviceCatalog" :label="$t('service_directory_mgmt')">
        <Card style="width:900px;margin:0 auto;">
          <Row slot="title">
            <Col span="18">
              <p>{{$t('service_directory')}}</p>
            </Col>
            <span class="serviceCatalog-margin-right">
              <Tooltip :content="$t('add_service_directory')" placement="top-start">
                <Button
                  size="small"
                  type="primary"
                  @click.stop.prevent="showServiceCatalogModal"
                  icon="md-add"
                ></Button>
              </Tooltip>
            </span>
          </Row>
          <Collapse accordion @on-change="getPipelineByCatalogueId">
            <Panel v-for="(item, index) in serviceCatalogues" :key="item.id" :name="item.id.toString()">
              <span><strong>{{ item.name }}</strong> - - {{ item.description }}</span>
              <span class="serviceCatalog-margin-right">
                <Tooltip :content="$t('delete_service_directory')" placement="top-start">
                  <Button
                    size="small"
                    type="error"
                    disabled
                    @click.stop.prevent="deleteAttr(item.id)"
                    icon="ios-trash"
                  ></Button>
                </Tooltip>
              </span>
              <span class="serviceCatalog-margin-right">
                <Tooltip :content="$t('add_service_channel')" placement="top-start">
                  <Button
                    size="small"
                    type="primary"
                    @click.stop.prevent="showPipelineModal(item.id)"
                    icon="md-add"
                  ></Button>
                </Tooltip>
              </span>
              <div slot="content">
                <Table border :columns="pipelineColumns" :data="item.pipelines"></Table>
              </div>
            </Panel>
          </Collapse>
        </Card>
      </TabPane>
    </Tabs>
    <Modal
      v-model="catalogModalVisible"
      :title="$t('add_service_directory')"
      footer-hide
      width="50"
      @on-cancel="catalogModalHide"
    >
      <div style="width:600px;margin:0 auto;">
        <Form ref="request" :model="catalogForm" :label-width="100">
          <FormItem :label="$t('service_directory_name')">
            <Input v-model="catalogForm.name" :placeholder="$t('service_directory_name')"></Input>
          </FormItem>
          <FormItem :label="$t('describe')">
            <Input v-model="catalogForm.description" :placeholder="$t('describe')"></Input>
          </FormItem>
          <FormItem> 
            <Button type="primary" @click="handlerSubmit">{{$t('submit')}}</Button>
            <Button style="margin-left: 8px" @click="handlerCancel">{{$t('cancle')}}</Button>
          </FormItem>
        </Form>
      </div>
    </Modal>
    <Modal
      v-model="pipelineModalVisible"
      :title="$t('add_service_channel')"
      footer-hide
      width="50"
      @on-cancel="pipelineModalHide"
    >
      <div style="width:600px;margin:0 auto;">
        <Form ref="request" :model="pipelineForm" :label-width="100">
          <FormItem :label="$t('service_channel_name')">
            <Input v-model="pipelineForm.name" :placeholder="$t('service_channel_name')"></Input>
          </FormItem>
          <FormItem :label="$t('processing_roles')">
            <Select @on-open-change="getAllRoles" v-model="pipelineForm.ownerRole">
              <Option
                v-for="role in allRoles"
                :key="role.name"
                :value="role.name"
              >{{role.displayName}}</Option>
            </Select>
          </FormItem>
          <FormItem :label="$t('describe')">
            <Input v-model="pipelineForm.description" :placeholder="$t('describe')"></Input>
          </FormItem>
          <FormItem> 
            <Button type="primary" @click="handlerPipelineSubmit">{{$t('submit')}}</Button>
            <Button style="margin-left: 8px" @click="handlerPipelineCancel">{{$t('cancle')}}</Button>
          </FormItem>
        </Form>
      </div>
    </Modal>
  </div>
</template>

<script>
import {
  getAllProcessDefinitionKeys,
  getAllAvailableServiceCatalogues,
  getServicePipelineByCatalogueId,
  createServiceRequestTemplate,
  createServiceCatalogue,
  createServicePipeline,
  getAllRoles,
  getAllAvailableServiceTemplate
} from "../api/server";

export default {
  name: "home",
  data() {
    return {
      addTemplateVisible: false,
      temData: [],
      temColumns: [
        {
          title: this.$t("template_name"),
          key: "name",
        },
        {
          title: this.$t("service_directory"),
          key: "serviceCatalogue",
        },
        {
          title: this.$t("service_channel"),
          key: "pipeline",
        },
        {
          title: this.$t("process"),
          key: "procDefName",
        },
        {
          title: this.$t("status"),
          key: "status",
        }
      ],
      currentTab: "templateDefinition",
      templateAttrs: [
        {
          id: 1,
          name: this.$t('task_name'),
          type: "text"
        },
        {
          id: 2,
          name: this.$t('reporter'),
          type: "text"
        },
        {
          id: 3,
          name: this.$t('reporting_time'),
          type: "date"
        },
        {
          id: 4,
          name: this.$t('emergency_level'),
          type: "select"
        },
        {
          id: 5,
          name: this.$t('mission_details'),
          type: "text"
        },
        {
          id: 6,
          name: this.$t('attachment'),
          type: "file"
        },
        {
          id: 7,
          name: this.$t('process_result'),
          type: "text"
        }
      ],
      pipelineColumns: [
        {
          title: this.$t('service_channel_name'),
          key: 'name'
        },
        {
          title: this.$t('status'),
          key: 'status'
        },
        {
          title: this.$t('describe'),
          key: 'description'
        },
      ],
      allRoles:[],
      serviceCatalogList: [],
      servicePipeline: [],
      serviceCatalogues: [],
      allProcessDefinitionKeys: [],
      catalogModalVisible:false,
      pipelineModalVisible:false,
      currentCatalog:'',
      catalogForm:{
        name:"",
        description:""
      },
      pipelineForm:{
        name:"",
        description:"",
        ownerRole:''
      },
      form: {
        name: "",
        description: "",
        procDefKey: "",
        servicePipelineId: "",
        procDefName: ""
      }
    };
  },
  methods: {
    addTemplateHandler () {
      this.addTemplateVisible = true
    },
    addTemplateModalHide () {
      this.addTemplateVisible = false
      this.form = {
        name: "",
        description: "",
        procDefKey: "",
        servicePipelineId: "",
        procDefName: ""
      }
    },
    handleTabClick() {},
    deleteAttr() {},
    addAttr() {},
    handlerCancel() {
      this.catalogModalVisible = false;
      this.catalogForm.name = ''
      this.catalogForm.description = ''
    },
    async handlerSubmit() {
      const {status} = await createServiceCatalogue(this.catalogForm)
      if(status === 'OK') {
        this.handlerCancel()
        this.getAllAvailableServiceCatalogues();
      }
    },
    catalogModalHide() {
      this.catalogModalVisible = false
    },
    showServiceCatalogModal() {
      this.catalogModalVisible = true
    },
    showPipelineModal(id) {
      this.currentCatalog = id,
      this.pipelineModalVisible = true
    },
    pipelineModalHide() {
      this.pipelineModalVisible = false
    },
    handlerPipelineCancel() {
      this.pipelineModalVisible = false;
      this.pipelineForm.name = ''
      this.pipelineForm.description = ''
      this.pipelineForm.ownerRole = ''
    },
    async handlerPipelineSubmit() {
      const payload = {
        ...this.pipelineForm,
        serviceCatalogueId:this.currentCatalog
      }
      const {status} = await createServicePipeline(payload)
      if(status === 'OK') {
        this.handlerPipelineCancel()
        this.getPipelineByCatalogueId([this.currentCatalog])
      }
    },
    processChanged (v) {
      if (v) {
        this.form.procDefName = this.allProcessDefinitionKeys.find(_ => _.procDefKey === v).procDefName
      }
    },
    async createServiceRequestTemplate() {
      const { data, status, message } = await createServiceRequestTemplate(
        this.form
      );
      if (status === "OK") {
        this.form = {
          name: "",
          description: "",
          procDefKey: "",
          servicePipelineId: "",
          procDefName: ""
        };
        this.addTemplateModalHide()
        this.getAllAvailableServiceTemplate()
      }
    },
    async getAllProcessDefinitionKeys() {
      const { data, status } = await getAllProcessDefinitionKeys();
      if (status === "OK") {
        this.allProcessDefinitionKeys = data;
      }
    },
    async getServicePipelineByCatalogueId(id) {
      const { data, status } = await getServicePipelineByCatalogueId(id);
      if (status === "OK") {
        this.servicePipeline = data;
      }
    },
    serviceCatalogChangeHandler(v) {
      if(v){
        this.getServicePipelineByCatalogueId(v)
        this.form.servicePipelineId = ''
      }
    },
    async getPipelineByCatalogueId(id) {
      if(id.length === 0) return
      const { data, status } = await getServicePipelineByCatalogueId(id[0]);
      if (status === "OK") {
        const found = this.serviceCatalogues.find(i=>i.id === id[0])
        found.pipelines = data
        // this.$set(found,'pipelines',data)
      }
    },
    async getAllAvailableServiceCatalogues() {
      const { data, status } = await getAllAvailableServiceCatalogues();
      if (status === "OK") {
        this.serviceCatalogues = data.map((item) => {
          return { 
            ...item,
            pipelines:[]
          }
        });;
      }
    },
    async getAllRoles() {
      const { data, status } = await getAllRoles()
      if(status === 'OK') {
        this.allRoles = data
      }
    },
    async getAllAvailableServiceTemplate () {
      const { data, status } = await getAllAvailableServiceTemplate()
      if(status === 'OK') {
        this.temData = data.map(_ => {
          return {
            ..._,
            serviceCatalogue: _.servicePipeline.serviceCatalogue.name,
            pipeline: _.servicePipeline.name
          }
        })
      }
    }
  },
  mounted() {
    this.getAllProcessDefinitionKeys();
    this.getAllAvailableServiceCatalogues();
    this.getAllRoles()
    this.getAllAvailableServiceTemplate()
  }
};
</script>
<style lang="scss">
.serviceCatalog-margin-right {
  margin-right: 20px;
  float: right;
}
</style>