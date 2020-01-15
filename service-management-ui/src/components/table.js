import "./table.scss";
import moment from "moment";
const DEFAULT_FILTER_NUMBER = 5;
const DATE_FORMAT = "YYYY-MM-DD HH:mm:ss";

export default {
  name: "PluginTable",
  props: {
    tableColumns: { default: () => [], require: true },
    tableData: { default: () => [] },
    showCheckbox: { default: () => true },
    highlightRow: { default: () => false },
    filtersHidden: { default: () => false },
    tableHeight: { default: () => "" },
    tableOuterActions: { default: () => [] },
    pagination: { type: Object }
  },
  data() {
    return {
      form: {},
      selectedRows: [],
      data: [],
      isShowHiddenFilters: false
    };
  },
  mounted() {

  },
  watch: {
    tableColumns: {
      handler(val, oldval) {
        this.tableColumns.forEach(_ => {
          this.$set(this.form, _.inputKey, "");
        });
      },
      deep: true,
      immediate: true
    }
  },
  computed: {},
  methods: {

    handleSubmit(ref) {
      const generateFilters = (type, i) => {
        switch (type) {
          case "select":
            filters.push({
              name: i,
              operator: "eq",
              value: this.form[i]
            });
            break;
          case "date":
            if (this.form[i][0] !== "" && this.form[i][1] !== "") {
              filters.push({
                name: i,
                operator: "gt",
                value: moment(this.form[i][0]).format(DATE_FORMAT)
              });
              filters.push({
                name: i,
                operator: "lt",
                value: moment(this.form[i][1]).format(DATE_FORMAT)
              });
            }
            break;
          default:
            filters.push({
              name: i,
              operator: "contains",
              value: this.form[i]
            });
            break;
        }
      };

      let filters = [];
      for (let i in this.form) {
        if (!!this.form[i] && this.form[i] !== "" && this.form[i] !== 0) {
          this.tableColumns
            .forEach(_ => {
              if (i === _.inputKey) {
                generateFilters(_.inputType, i);
              }
            });
        }
      }
      this.$emit("handleSubmit", filters);
    },
    reset(ref) {
      this.tableColumns.forEach(_ => {
        if (_.children) {
          _.children.forEach(j => {
            if (!j.isNotFilterable) {
              this.form[j.inputKey] = "";
            }
          });
        } else {
          if (!_.isNotFilterable) {
            this.form[_.inputKey] = "";
          }
        }
      });
    },
    getTableOuterActions() {
      return (
        this.tableOuterActions &&
        this.tableOuterActions.map(_ => {
          return (
            <Button
              style="margin-right: 10px"
              {..._}
              onClick={() => {
                this.$emit("actionFun", _.actionType, this.selectedRows);
              }}
            >
              {_.label}
            </Button>
          );
        })
      );
    },
    renderFormItem(item, index = 0) {
      if (item.isNotFilterable) return;
      const data = {
        props: {
          ...item
        },
        style: {
          width: "100%"
        }
      };

      let renders = item => {
          return (
            <item.component
              value={this.form[item.inputKey]}
              onInput={v => (this.form[item.inputKey] = v)}
              {...data}
            />
          );
      };
      return (
        <Col
          span={item.span || 3}
          class={
            index < DEFAULT_FILTER_NUMBER
              ? ""
              : this.isShowHiddenFilters
              ? "hidden-filters-show"
              : "hidden-filters"
          }
        >
          <FormItem label={item.title} prop={item.inputKey} key={item.inputKey}>
            {renders(item)}
          </FormItem>
        </Col>
      );
    },
    getFormFilters() {
      return (
        <Form ref="form" label-position="top" inline>
          <Row>
            {this.tableColumns
              .map((_, index) => {
                if (_.children) {
                  return (
                    <Row>
                      <Col span={3}>
                        <strong>{_.title}</strong>
                      </Col>
                      <Col span={21}>
                        {_.children
                          .map(j => this.renderFormItem(j))}
                      </Col>
                    </Row>
                  );
                }
                return this.renderFormItem(_, index);
              })}
            <Col span={6}>
              <div style="display: flex; margin-bottom: 20px">
                {this.tableColumns.length > DEFAULT_FILTER_NUMBER &&
                  (!this.isShowHiddenFilters ? (
                    <FormItem style="position: relative; bottom: -22px;">
                      <Button
                        type="info"
                        ghost
                        shape="circle"
                        icon="ios-arrow-down"
                        onClick={() => {
                          this.isShowHiddenFilters = true;
                        }}
                      >
                        {this.$t('more_filter')}
                      </Button>
                    </FormItem>
                  ) : (
                    <FormItem style="position: relative; bottom: -22px;">
                      <Button
                        type="info"
                        ghost
                        shape="circle"
                        icon="ios-arrow-up"
                        onClick={() => {
                          this.isShowHiddenFilters = false;
                        }}
                      >
                        {this.$t('less_filter')}
                      </Button>
                    </FormItem>
                  ))}

                <FormItem style="position: relative; bottom: -22px;">
                  <Button
                    type="primary"
                    icon="ios-search"
                    onClick={() => this.handleSubmit("form")}
                  >
                    {this.$t('search')}
                  </Button>
                </FormItem>
                <FormItem style="position: relative; bottom: -22px;">
                  <Button icon="md-refresh" onClick={() => this.reset("form")}>
                  {this.$t('reset')}
                  </Button>
                </FormItem>
              </div>
            </Col>
          </Row>
        </Form>
      );
    },
    onCheckboxSelect(selection) {
      this.selectedRows = selection;
      this.$emit("getSelectedRows", selection, false);
    },
    onRadioSelect(current, old) {
      this.$emit("getSelectedRows", [current], false);
    },
    cancelSelected() {
      this.$refs["table"].selectAll(false);
      this.selectedRows = [];
    },
    sortHandler(sort) {
      this.$emit("sortHandler", sort);
    },
    export(data) {
      this.$refs.table.exportCsv({
        filename: data.filename,
        columns: this.tableColumns,
        data: data.data
      });
    },
  },
  render(h) {
    const {
      tableData,
      tableHeight,
      tableColumns,
      pagination,
      highlightRow,
      filtersHidden
    } = this;
    return (
      <div>
        {!filtersHidden && <div>{this.getFormFilters()}</div>}
        <Row style="margin-bottom:10px">{this.getTableOuterActions()}</Row>
        <Table
          ref="table"
          border
          data={tableData}
          columns={tableColumns}
          max-height={tableHeight}
          highlight-row={highlightRow}
          on-on-selection-change={this.onCheckboxSelect}
          on-on-current-change={this.onRadioSelect}
          on-on-sort-change={this.sortHandler}
          size="small"
        />
        {pagination && (
          <Page
            total={pagination.total}
            page-size={pagination.pageSize}
            current={pagination.currentPage}
            on-on-change={v => this.$emit("pageChange", v)}
            on-on-page-size-change={v => this.$emit("pageSizeChange", v)}
            show-elevator
            show-sizer
            show-total
            style="float: right; margin: 10px 0;"
          />
        )}
      </div>
    );
  }
};
