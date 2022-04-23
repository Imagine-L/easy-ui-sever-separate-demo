<template>
  <el-container>
    <el-aside width="790px"
    >
      <el-image
          fit="fill"
          :src="require('@/assets/image/login_left.jpg')"
      ></el-image
      >
    </el-aside>
    <el-main>
      <el-card class="box-card login-card">
        <span class="login-title">元动力后台管理系统</span>
        <span class="login-tip">welcome 欢迎登陆</span>
        <el-form
            ref="user"
            :model="user"
            :rules="rules"
            label-width="80px"
        >
          <el-form-item label="用户名" prop="userName">
            <el-input
                v-model="user.userName"
                placeholder="请输入用户"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
                v-model="user.password"
                type="password"
                placeholder="请输入密码"
                show-password
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="doLogin">登陆</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import {ElMessage} from 'element-plus'
import {actions} from "@/constant";
import {getUserInfo, getUserById} from "@/api/user";

export default {
  data() {
    return {
      user: {
        userName: "ydl_admin",
        password: "123456"
      },
      rules: {
        userName: [
          {
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
          },
          {
            min: 5,
            max: 15,
            message: '用户名长度需要在5-15之间',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '密码不能为空',
            trigger: 'blur',
          },
        ]
      }
    }
  },
  methods: {
    doLogin: function () {
      this.$refs.user.validate(valid => {
        if (valid) {
          this.$store.dispatch(actions.LOGIN, this.user).then(resp => {
            if (resp.status === 200) {
              this.$store.dispatch(actions.GET_USER_INFO).then(() => {
                this.$router.push({name: 'main'})
              })

              ElMessage({
                message: `欢迎您，${resp.data.user.userName}`,
                type: 'success',
              })
            }
          }).catch(() => {
            ElMessage.error('请检查用户名或密码是否错误!')
          })
        } else {
          ElMessage({
            message: '用户名或密码不符合要求!',
            type: 'warning',
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.el-image {
  height: 885px;
  width: 750px;
}

.el-main {
  position: relative;
}

.login-card {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  width: 480px;
  height: 400px;
  padding: 50px;
}

.login-title {
  width: 459px;
  height: 70px;
  font-size: 40px;
  font-family: AlibabaPuHuiTiB;
  color: #333333;
  line-height: 90px;
  letter-spacing: 1px;
  font-weight: 800;
  display: block;
  text-align: left;
}

.login-tip {
  width: 319px;
  height: 70px;
  font-size: 30px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #999999;
  line-height: 90px;
  letter-spacing: 1px;
  display: block;
  text-align: left;
  margin-bottom: 30px;
}
</style>