<template>
  <div>
    <Tabs type="card" :value="currentTab" closable @on-click="handleTabClick">
      <TabPane :closable="false" name="templateDefinition" label="模板定义">
        <Card style="width:900px;margin:0 auto;">
          <Row slot="title">
            <Col span="18">
              <p>模板定义</p>
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
          <FormItem label="审批流程" prop="attrName">
            <Input v-model="form.name" disabled></Input>
          </FormItem>
        </Form>
        <div style="margin:30px auto;text-align:center">
          <Button type="primary">提交</Button>
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
                    @click.stop.prevent="deleteAttr(item.id)"
                    icon="md-add"
                  ></Button>
              </Tooltip>
            </span>
          </Row>
          <Collapse accordion>
            <Panel v-for="(item, index) in templateAttrs" :key="item.id" :name="index.toString()">
              <span>{{ item.name }}</span>
              <span class="serviceCatalog-margin-right">
                <Tooltip content="删除服务目录" placement="top-start">
                  <Button
                    size="small"
                    type="error"
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
                    @click.stop.prevent="deleteAttr(item.id)"
                    icon="md-add"
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
        </Card>
      </TabPane>
    </Tabs>
  </div>
</template>

<script>
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
        }
      ],
      serviceCatalogList: [],
      form: {
        name: ""
      }
    };
  },
  methods: {
    handleTabClick() {},
    deleteAttr() {},
    addAttr() {}
  }
};
</script>
<style lang="scss">
.serviceCatalog-margin-right {
  margin-right: 20px;
  float: right;
}
</style>