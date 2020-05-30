<template>
  <el-card style="margin-left:500px;margin-right:500px;margin-top:200px">
    <p style="margin-left:120px">注册界面</p>
    <el-form :model="rform" label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="rform.uname" placeholder="你的个性化名字"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="rform.upwd" autocomplete="off" placeholder="你的个性化密码"></el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input type="password" v-model="rform.checkPwd" autocomplete="off" placeholder="再输入一次密码"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="info" plain @click="resetForm()">重置</el-button>
        <el-button type="warning" plain @click="submitInfo()" style="margin-left:30px">注册</el-button>
        <el-button @click="back()" style="margin-left:30px">返回</el-button>
        
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      rform:{
        uname:"",
        upwd:"",
        checkPwd:""
      }
    };
  },
  methods: {
    back(){
      this.$router.push("/");
    },

    resetForm(){
      this.rform.uname = "";
      this.rform.upwd = "";
      this.rform.checkPwd = "";
      return false;
    },

    submitInfo() {
      if (this.rform.uname.trim() === "") {
        this.$alert("用户名不能为空", "提示", {
          confirmButtonText: "确定"
        });
        this.rform.uname = "";
        this.rform.upwd = "";
        this.rform.checkPwd = "";
        return false;
      } else if (this.rform.upwd.trim() === "") {
        this.$alert("密码不能为空", "提示", {
          confirmButtonText: "确定"
        });
        this.rform.upwd = "";
        this.rform.checkPwd = "";
        return false;
      } else if (this.rform.upwd != this.rform.checkPwd) {
        this.$alert("两次密码不一致,请重新输入", "提示", {
          confirmButtonText: "确定"
        });
        this.rform.upwd = "";
        this.rform.checkPwd = "";
        return false;
      }
      this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "POST",
        url: "api/register",
        data: this.$qs.stringify({
          uname: this.rform.uname,
          upwd: this.rform.upwd
        })
      })
        .then(res => {
          console.log(res.data);
          if (res.data == true) {
            this.$alert("注册成功", "提示", {
              confirmButtonText: "确定"
            });
            this.$router.push("/");
          } else {
            this.$alert("注册失败,请重新创建", "提示", {
              confirmButtonText: "确定"
            });
          }
        })
        .catch(Error => {
          console.log(Error);
        });
    }
  }
};
</script>
