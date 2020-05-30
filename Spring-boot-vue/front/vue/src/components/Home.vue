<template>
  <el-container style="margin-top:100px">
    <!-- 侧边栏 -->
    <el-aside width="210px">
      <div style="margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px">
        <el-button style="font-size:15px; width:190px" type="warning" @click="loginOut()" round>退出登录</el-button>
        <br /><br />用户id:{{uno}}<br/>用户名:{{uname}}<br />位置:商品购买页<br /><br />
        <el-button type="primary" style="font-size:15px; width:190px" @click="createOrder()" round>上架商品</el-button>
        <br />
        <br />
        <el-button type="primary" style="font-size:15px; width:190px" @click="myOrder()" round>我的订单</el-button>
        <br />
        <br />
        <el-button type="primary" style="font-size:15px; width:190px" @click="myCommodity()" round>我的商品</el-button>
      </div>
    </el-aside>

    <!-- 主要部分 -->
    <el-main>
      <el-card>
        <!-- 列表头 -->
        <el-row gutter="1">
          <el-col :span="3">
            <div>序号</div>
          </el-col>
          <el-col :span="3">
            <div>名称</div>
          </el-col>
          <el-col :span="3">
            <div>价格/元</div>
          </el-col>
          <el-col :span="3">
            <div>描述</div>
          </el-col>
          <el-col :span="3">
            <div>出售状态</div>
          </el-col>
          <el-col :span="3">
            <div>发售时间</div>
          </el-col>
        </el-row>
        <!-- 列表 -->
        <li v-for="(item,index) in listForm" :key="item" >
          <el-row gutter="1">
            <div>
              <el-col :span="3">
                <div>{{item.cno}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{showfull(item.cname)}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{item.cprice.toFixed(2)}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{showfull(item.cinformation)}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{getstate(item.cstate)}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{item.ctime}}</div>
              </el-col>

              <el-col :span="2" >
                <div>
                  <el-button type="primary" @click="indexData=index;drawer = true">详情</el-button>
                  <el-drawer
                    title="商品详情"
                    :visible.sync="drawer"
                    :direction="direction"
                    :before-close="handleClose"
                  >
                    <el-form :model="form">
                      <el-form-item label="序号： " :label-width="formLabelWidth">{{listForm[indexData].cno}}</el-form-item>
                      <el-form-item label="商品名称: " :label-width="formLabelWidth">{{listForm[indexData].cname}}</el-form-item>
                      <el-form-item label="价格/元: " :label-width="formLabelWidth">{{listForm[indexData].cprice.toFixed(2)}}
                      </el-form-item>
                      <el-form-item label="描述: " :label-width="formLabelWidth">{{listForm[indexData].cinformation}}</el-form-item>
                      <el-form-item label="售出状态: " :label-width="formLabelWidth">{{getstate(listForm[indexData].cstate)}}
                      </el-form-item>
                      <el-form-item label="发售时间: " :label-width="formLabelWidth">{{listForm[indexData].ctime}}</el-form-item>
                    </el-form>
                  </el-drawer>
                </div>
              </el-col>

              <el-col :span="2" >
                <div>
                  <el-button type="success" @click="indexData=index;prepareBuy(listForm[index].uno,listForm[index].cstate)">
                    购买</el-button>
                  <el-dialog
                    title="购买确认"
                    :visible.sync="dialogVisible"
                    width="30%"
                    :before-close="handleClose"
                  >
                  商品: {{listForm[indexData].cname}}<br>
                  价格: {{listForm[indexData].cprice.toFixed(2)}}元<br><br>
                    <el-button type="success" @click="onBuy(listForm[indexData].cno,indexData)">确认购买</el-button>
                    <el-button type="warning" @click="dialogVisible = false">取消购买</el-button>
                  </el-dialog>
                </div>
              </el-col>
            </div>
          </el-row>
        </li>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      drawer: false,
      dialogVisible: false,
      uno: this.$route.query.uno,
      uname: this.$route.query.uname,
      indexData:'0',
      listForm: this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "post",
        url: "api/allCommodity"
      })
        .then(res => {
          this.listForm = res.data;
        })
        .catch(Error => {
          console.log(Error);
        })
    };
  },
  methods: {
    getstate(indexstate){
      if (indexstate === 1){
        return "已锁定"
      }
      else if (indexstate === 2){
        return "已售空"
      }
      else{
        return "无人购买"
      }
    },
    prepareBuy(sellerno,buystate){
      if(buystate == 1 || buystate == 2){
        this.$alert("此商品当前无法购买", "提示", {
          confirmButtonText: "确定"
        });
      }
      else if(sellerno == this.uno){
        this.$alert("不能购买自己的商品", "提示", {
          confirmButtonText: "确定"
        });
      }
      else{
        this.dialogVisible = true
      }
    },
    onBuy(no,index) {
      this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "POST",
        url: "api/buyCommodity",
        data: this.$qs.stringify({ 
          cno: no,
          uno: this.uno})
      })
        .then(res => {
          console.log(res.data);
          if (res.data == true) {
            this.$alert("购买成功", "提示", {
              confirmButtonText: "确定"
            });
            this.listForm[index].cstate = 1;
            this.dialogVisible = false;
          } else {
            this.$alert("购买失败请重试", "提示", {
              confirmButtonText: "确定"
            });
            this.dialogVisible = false;
          }
        })
        .catch(Error => {
          this.$alert("购买失败请重试", "提示", {
            confirmButtonText: "确定"
          });
          console.log(Error);
        });
    },
    
    showfull(text){
      if (text.length > 7){
        return text.substr(0, 5) + '...'
      }
      else{
        return text
      }
    },

    loginOut() {
      this.uno = "";
      this.uname = "";
      this.$router.push("/");
    },

    //创建
    createOrder() {
      this.$router.push({
        name: "CreateOrder",
        query: {
          uname: this.uname,
          uno: this.uno
        }
      });
    },

    //查看订单
    myOrder() {
      this.$router.push({
        name: "MyOrder",
        query: {
          uname: this.uname,
          uno: this.uno
        }
      });
    },

    //查看购买的商品
    myCommodity() {
      this.$router.push({
        name: "MyCommodity",
        query: {
          uname: this.uname,
          uno: this.uno
        }
      });
    }
  }
};
</script>
