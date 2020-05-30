<template>
  <el-card style="margin-left:500px;margin-right:500px;margin-top:100px">
    <p style="margin-left:120px">上架商品</p>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="商品名称: ">
        <el-input v-model="form.cname" placeholder="这里输入商品名称"></el-input>
      </el-form-item>

      <el-form-item label="价格/元: ">
        <el-input-number v-model="form.cprice" :precision="2"></el-input-number>
      </el-form-item>

      <el-form-item label="详细信息: ">
        <el-input type="textarea" :rows="5" placeholder="这里输入联系方式、商品描述" v-model="form.cinformation"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="success" @click="onSubmit()">提交</el-button>
        <el-button type="warning" @click="back()">取消并返回</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
export default {
  data() {
    return {
      uname:this.$route.query.uname,
      form: {
        uno: this.$route.query.uno,
        cname: "",
        cprice: "",
        cinformation: "",
      }
    };
  },
  methods: {
    onSubmit() {
      if (this.form.cname.trim() === "") {
        this.$alert("商品名字不能为空", "提示", {
          confirmButtonText: "确定"
        });
        return false;
      } else if (this.form.cinformation.trim() === "") {
        this.$alert("你详情至少打一个字鸭", "提示", {
          confirmButtonText: "确定"
        });
        return false;
      }
      this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "POST",
        url: "api/addCommodity",
        data: this.$qs.stringify(this.form)
      })
        .then(res => {
          //获取response信息
          console.log(res.data);
          if (res.data == false) {
            this.form.cname = "";
            this.form.cprice = 0;
            this.form.cinformation = "";
            this.$alert("商品发布失败，请重新输入", "提示", {
              confirmButtonText: "确定"
            });
            
          } else {
            this.$alert("商品发布成功", "提示", {
              confirmButtonText: "确定"
            });
            this.$router.push({
              name: "Home",
              query: {
                uname: this.uname,
                uno: this.form.uno
              }
            });
          }
        })
        .catch(Error => {
          console.log(Error);
        });
    },

    back() {
      this.form.cname = "";
      this.form.cprice = 0;
      this.form.cinformation = "";
      this.$router.go(-1);
    }
  }
};
</script>