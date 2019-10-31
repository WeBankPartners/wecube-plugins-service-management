<template>
  <div>
    <Select
      :value="value"
      :multiple="isMultiple"
      filterable
      clearable
      @on-change="changeValue"
    >
      <Option v-for="item in opts" :value="item.value" :key="item.value">{{
        item.label
      }}</Option>
    </Select>
  </div>
</template>
<script>
const DEFAULT_TAG_NUMBER = 2;
import { queryReferenceEnumCodes } from "@/api/server";
export default {
  name: "PluginSelect",
  props: {
    value: {},
    isMultiple: { default: () => false },
    options: { default: () => [] },
    maxTags: { default: () => DEFAULT_TAG_NUMBER },
  },
  data() {
    return {};
  },
  watch: {},
  computed: {
    opts() {
      return this.options;
    }
  },
  mounted() {},
  methods: {
    formatOptions() {},
    changeValue(val) {
      this.$emit("input", val);
      this.$emit("change", val);
    },
  }
};
</script>
