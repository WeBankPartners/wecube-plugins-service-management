<template>
  <div>
    <Tabs type="card" :value="currentTab" closable @on-click="handleTabClick">
      <TabPane :closable="false" name="templateDefinition" label="模板定义">
        <Card style="width:600px;margin:0 auto;">
          <Row slot="title">
            <Col span="18">
              <p>模板属性</p>
            </Col>
          </Row>
          <Collapse accordion>
            <Panel v-for="(item, index) in templateAttrs" :key="item.id" :name="index.toString()">
              <span>{{ item.name }}</span>
              <span class="serviceCatalog-margin-right">
                <Tooltip content="删除属性" placement="top-start">
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
                  <FormItem label="属性名称" prop="attrName">
                    <Input v-model="item.name" disabled></Input>
                  </FormItem>
                  <FormItem label="类型" prop="type">
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
          <FormItem label="模板名称" prop="attrName">
            <Input v-model="form.name"></Input>
          </FormItem>
          <FormItem label="审批流程" prop="attrName">
            <Select v-model="form.processDefinitionKey">
              <Option
                v-for="process in allProcessDefinitionKeys"
                :key="process.processDefinitionKey"
                :value="process.processDefinitionKey"
              >{{process.processDefinitionKey}}</Option>
            </Select>
          </FormItem>
          <FormItem label="服务通道" prop="attrName">
            <Select v-model="form.servicePipelineId">
              <Option
                v-for="pipeline in servicePipeline"
                :key="pipeline.id"
                :value="pipeline.id"
              >{{pipeline.name}}</Option>
            </Select>
          </FormItem>
          <FormItem label="描述" prop="attrName">
            <Input type="textarea" v-model="form.description"></Input>
          </FormItem>
        </Form>
        <div style="margin:30px auto;text-align:center">
          <Button type="primary" @click="createServiceRequestTemplate">提交</Button>
        </div>
      </TabPane>
      <TabPane :closable="false" name="serviceCatalog" label="服务目录管理">
        <Card style="width:900px;margin:0 auto;">
          <Row slot="title">
            <Col span="18">
              <p>服务目录</p>
            </Col>
            <span class="serviceCatalog-margin-right">
              <Tooltip content="新增服务目录" placement="top-start">
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
              <span>{{ item.name }}</span>
              <span class="serviceCatalog-margin-right">
                <Tooltip content="删除服务目录" placement="top-start">
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
                <Tooltip content="新增服务通道" placement="top-start">
                  <Button
                    size="small"
                    type="primary"
                    @click.stop.prevent="showPipelineModal(item.id)"
                    icon="md-add"
                  ></Button>
                </Tooltip>
              </span>
              <div slot="content">
                <Tag type="dot" color="primary" v-for="pipeline in item.pipelines" :key="pipeline.id">{{pipeline.name}}</Tag>
              </div>
            </Panel>
          </Collapse>
        </Card>
      </TabPane>
    </Tabs>
    <Modal
      v-model="catalogModalVisible"
      title="服务目录新增"
      footer-hide
      width="50"
      @on-cancel="catalogModalHide"
    >
      <div style="width:600px;margin:0 auto;">
        <Form ref="request" :model="catalogForm" :label-width="100">
          <FormItem label="服务目录名称">
            <Input v-model="catalogForm.name" placeholder="服务目录名称"></Input>
          </FormItem>
          <FormItem label="描述">
            <Input v-model="catalogForm.description" placeholder="描述"></Input>
          </FormItem>
          <FormItem> 
            <Button type="primary" @click="handlerSubmit">提交</Button>
            <Button style="margin-left: 8px" @click="handlerCancel">取消</Button>
          </FormItem>
        </Form>
      </div>
    </Modal>
    <Modal
      v-model="pipelineModalVisible"
      title="服务通道新增"
      footer-hide
      width="50"
      @on-cancel="pipelineModalHide"
    >
      <div style="width:600px;margin:0 auto;">
        <Form ref="request" :model="pipelineForm" :label-width="100">
          <FormItem label="服务通道名称">
            <Input v-model="pipelineForm.name" placeholder="服务通道名称"></Input>
          </FormItem>
          <FormItem label="处理角色">
            <Select v-model="pipelineForm.ownerRoleId">
              <Option
                v-for="role in allRoles"
                :key="role.roleId"
                :value="role.roleId"
              >{{role.description}}</Option>
            </Select>
          </FormItem>
          <FormItem label="描述">
            <Input v-model="pipelineForm.description" placeholder="描述"></Input>
          </FormItem>
          <FormItem> 
            <Button type="primary" @click="handlerPipelineSubmit">提交</Button>
            <Button style="margin-left: 8px" @click="handlerPipelineCancel">取消</Button>
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
  getAllRoles
} from "../api/server";

export default {
  name: "home",
  data() {
    return {
      currentTab: "templateDefinition",
      templateAttrs: [
        {
          id: 1,
          name: "任务名称",
          type: "text"
        },
        {
          id: 2,
          name: "上报人",
          type: "text"
        },
        {
          id: 3,
          name: "上报时间",
          type: "date"
        },
        {
          id: 4,
          name: "紧急程度",
          type: "select"
        },
        {
          id: 5,
          name: "任务描述",
          type: "text"
        },
        {
          id: 6,
          name: "任务附件",
          type: "file"
        },
        {
          id: 7,
          name: "处理结果",
          type: "text"
        }
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
        ownerRoleId:''
      },
      form: {
        name: "",
        description: "",
        processDefinitionKey: "",
        servicePipelineId: ""
      }
    };
  },
  methods: {
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
      this.pipelineForm.ownerRoleId = ''
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
    async createServiceRequestTemplate() {
      const { data, status, message } = await createServiceRequestTemplate(
        this.form
      );
      if (status === "OK") {
        this.form = {
          name: "",
          description: "",
          processDefinitionKey: "",
          servicePipelineId: ""
        };
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
    async getPipelineByCatalogueId(id) {
      if(id.length === 0) return
      const { data, status } = await getServicePipelineByCatalogueId(id[0]*1);
      if (status === "OK") {
        const found = this.serviceCatalogues.find(i=>i.id === id[0]*1)
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
  },
  mounted() {
    this.getAllProcessDefinitionKeys();
    this.getServicePipelineByCatalogueId(999);
    this.getAllAvailableServiceCatalogues();
    this.getAllRoles()
  }
};
</script>
<style lang="scss">
.serviceCatalog-margin-right {
  margin-right: 20px;
  float: right;
}
</style>