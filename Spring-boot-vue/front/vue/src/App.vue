<template>
  <div :style="note">
    <el-container>
      <el-main>
        <router-view />
      </el-main>
      <el-footer/>
      <el-footer/>
      <el-footer/>
      <el-footer/>
      <el-footer/>
      <el-footer/>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      note:{
        backgroundImage:'url(' + require('./assets/backgroud2.png') + ')',
        backgroundRepeat:'no-repeat',
        backgroundSize:'100%'
      }
    }
  }
};
</script>

<style>
.el-aside {
  margin-top: 8px;
  font-size: 23px;
  background-color: rgb(9, 219, 226);
}
</style>
