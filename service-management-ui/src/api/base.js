import Vue from "vue";
import axios from "axios";
const baseURL = "";
const req = axios.create({
  withCredentials: true,
  baseURL,
  timeout: 50000
});

req.defaults.headers.common["Http-Client-Type"] = "Ajax";
// req.defaults.headers.common["Authorization"] = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1bWFkbWluIiwiaWF0IjoxNTk0NjIyMTgxLCJ0eXBlIjoiYWNjZXNzVG9rZW4iLCJjbGllbnRUeXBlIjoiVVNFUiIsImV4cCI6MTU5NDYyMzM4MSwiYXV0aG9yaXR5IjoiW1NVUEVSX0FETUlOLElNUExFTUVOVEFUSU9OX1dPUktGTE9XX0VYRUNVVElPTixJTVBMRU1FTlRBVElPTl9CQVRDSF9FWEVDVVRJT04sSU1QTEVNRU5UQVRJT05fQVJUSUZBQ1RfTUFOQUdFTUVOVCxNT05JVE9SX01BSU5fREFTSEJPQVJELE1PTklUT1JfTUVUUklDX0NPTkZJRyxNT05JVE9SX0NVU1RPTV9EQVNIQk9BUkQsTU9OSVRPUl9BTEFSTV9DT05GSUcsTU9OSVRPUl9BTEFSTV9NQU5BR0VNRU5ULENPTExBQk9SQVRJT05fUExVR0lOX01BTkFHRU1FTlQsQ09MTEFCT1JBVElPTl9XT1JLRkxPV19PUkNIRVNUUkFUSU9OLEFETUlOX1NZU1RFTV9QQVJBTVMsQURNSU5fUkVTT1VSQ0VTX01BTkFHRU1FTlQsQURNSU5fVVNFUl9ST0xFX01BTkFHRU1FTlQsQURNSU5fQ01EQl9NT0RFTF9NQU5BR0VNRU5ULENNREJfQURNSU5fQkFTRV9EQVRBX01BTkFHRU1FTlQsQURNSU5fUVVFUllfTE9HLE1FTlVfQURNSU5fUEVSTUlTU0lPTl9NQU5BR0VNRU5ULE1FTlVfQVBQTElDQVRJT05fQVJDSElURUNUVVJFX0RFU0lHTixNRU5VX0RFU0lHTklOR19DSV9JTlRFR1JBVEVEX1FVRVJZX0VYRUNVVElPTixNRU5VX0FETUlOX0NNREJfTU9ERUxfTUFOQUdFTUVOVCxNRU5VX0lEQ19QTEFOTklOR19ERVNJR04sTUVOVV9ERVNJR05JTkdfQ0lfREFUQV9FTlFVSVJZLE1FTlVfSURDX1JFU09VUkNFX1BMQU5OSU5HLE1FTlVfQVBQTElDQVRJT05fREVQTE9ZTUVOVF9ERVNJR04sTUVOVV9ERVNJR05JTkdfQ0lfSU5URUdSQVRFRF9RVUVSWV9NQU5BR0VNRU5ULE1FTlVfQVBQTElDQVRJT05fQVJDSElURUNUVVJFX1FVRVJZLE1FTlVfQ01EQl9BRE1JTl9CQVNFX0RBVEFfTUFOQUdFTUVOVCxNRU5VX0RFU0lHTklOR19DSV9EQVRBX01BTkFHRU1FTlQsTUVOVV9BRE1JTl9RVUVSWV9MT0csSk9CU19TRVJWSUNFX0NBVEFMT0dfTUFOQUdFTUVOVCxKT0JTX1RBU0tfTUFOQUdFTUVOVCxDTURCX0FETUlOLE1PTklUT1JfQURNSU4sUFJEX09QUyxTVEdfT1BTXSJ9.IZCOyiIGyABnAWKQ2FdEg3eQ3HgFQTbJ03STA3lbg_gQNNIV3tUa_nKsKLwa6TXlpMglGwaO4M_Re0FoiSKIMw";
const throwError = res => new Error(res.message || "error");
req.interceptors.response.use(
  res => {
    if (res.status === 200) {
      if (res.headers["cas-redirect-flag"] === "true") {
        const currentUrl = window.location.href;
        if (currentUrl.indexOf("ticket=") !== -1) return;
        window.location.href =
          res.headers.location.split("?")[0] + "?service=" + currentUrl;
      }
      if (res.data.status === "ERROR") {
        const errorMes = Array.isArray(res.data.data)
          ? res.data.data.map(_ => _.errorMessage).join("<br/>")
          : res.data.message;
        Vue.prototype.$Notice.error({
          title: "Error",
          desc: errorMes,
          duration: 0
        });
      }
      return {
        ...res.data,
        user: res.headers["current_user"] || " - "
      };
    } else {
      return {
        data: throwError(res)
      };
    }
  },
  res => {
    const { response } = res;
    Vue.prototype.$Notice.error({
      title: "error",
      desc:
        (response.data &&
          "status:" +
            response.data.status +
            "<br/> error:" +
            response.data.error +
            "<br/> message:" +
            response.data.message) ||
        "error"
    });
    return new Promise((resolve, reject) => {
      resolve({
        data: throwError(res)
      });
    });
  }
);


export default req;
