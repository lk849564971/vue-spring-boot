<template>
  <div>
    <el-card style="margin-left:550px;margin-right:550px;margin-top:200px">
      <p style="margin-left:120px">登录界面</p>
      <el-form label-width="50px" >
        <el-form-item label="账号" >
          <el-input v-model="loginForm.uname" autocomplete="off" placeholder="这里输入你的账号鸭"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="loginForm.upwd"  autocomplete="off" placeholder="这里输入你的密码鸭" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" @click="toRegister" icon="el-icon-circle-plus-outline" plain>注册</el-button>
          <el-button type="primary" @click="login" style="margin-left:40px;" icon="el-icon-key" plain>登陆</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        uname: "",
        upwd: ""
      }
    };
  },
  methods: {
    toRegister() {
      this.$router.push("/register");
    },
    login() {
      if (this.loginForm.uname.trim() === "") {
        this.$alert("用户名不能为空", "提示", {
          confirmButtonText: "确定"
        });
        this.loginForm.uname = "";
        this.loginForm.upwd = "";
        return false;
      } else if (this.loginForm.upwd.trim() === "") {
        this.$alert("密码不能为空", "提示", {
          confirmButtonText: "确定"
        });
        this.loginForm.upwd = "";
        return false;
      }
      this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "POST",
        url: "api/login",
        data: this.$qs.stringify(this.loginForm)
      })
        .then(res => {
          console.log(res.data);
          if (res.data != false) {
            this.$router.push({
              name: "Home",
              query: {
                uname: this.loginForm.uname,
                uno: res.data
              }
            });
          }
          else{
            (this.loginForm.upwd = ""),
            this.$alert("用户名或者密码错误", "提示", {
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
