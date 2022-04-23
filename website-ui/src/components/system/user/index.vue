<template>
  <el-form :inline="true" :model="queryForm" class="demo-form-inline">
    <el-form-item label="userId">
      <el-input v-model="queryForm.userId" placeholder="input userId"/>
    </el-form-item>
    <el-form-item label="nickName">
      <el-input v-model="queryForm.nickName" placeholder="input nickName"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
    <el-form-item>
      <!--      <el-button :icon="Search" circle />-->
      <el-button type="success" :icon="Plus" circle @click="tableInit"/>
      <!--      <el-button type="success" :icon="Check" circle />-->
      <el-button type="info" :icon="Message" circle/>
      <el-button type="warning" :icon="Star" circle/>
      <el-button type="danger" :icon="Delete" circle/>
    </el-form-item>
  </el-form>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column label="编号" width="180">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span style="margin-left: 10px">{{ scope.row.userId }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="昵称" width="180">
      <template #default="scope">
        <el-popover effect="light" trigger="hover" placement="top" width="auto">
          <template #default>
            <div>name: {{ scope.row.userName }}</div>
            <div>address: {{ scope.row.userName }}</div>
          </template>
          <template #reference>
            <el-tag>{{ scope.row.nickName }}</el-tag>
          </template>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column label="电话" width="180">
      <template #default="scope">
        <div style="display: flex;">
          <span style="margin-left: 10px">{{ scope.row.phonenumber }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="邮箱" width="180">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span style="margin-left: 10px">{{ scope.row.email }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row.userId, scope.row)"
        >修改
        </el-button
        >
        <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row.userId, scope.row)"
        >删除
        </el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <br>
  <div class="demo-pagination-block">
    <el-pagination
        v-model:currentPage="queryForm.pageNo"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[2, 3, 4, 5]"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
  <el-dialog v-model="dialogFormVisible" :title="dialogTitle">
    <el-form :model="newUser">
      <el-form-item label="用户名" :label-width="200">
        <el-input v-model="newUser.userName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="昵称" :label-width="200">
        <el-input v-model="newUser.nickName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="密码" :label-width="200">
        <el-input v-model="newUser.password" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="确认密码" :label-width="200">
        <el-input v-model="newUser.againPwd" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUserSubmit"
        >保存</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref} from 'vue'
import {onMounted} from "vue";
import {getUserList, saveUser, deleteUser, updateUser, getUserById} from "@/api/user";
import {
  Check,
  Delete,
  Plus,
  Message,
  Search,
  Star,
} from '@element-plus/icons-vue'
import {ElMessage,ElMessageBox} from "element-plus";

let dialogFormVisible = ref(false)
let dialogTitle = ref('')

const newUser = ref({
  userId: null,
  userName: '',
  nickName: '',
  password: '',
  againPwd: ''
})

const queryForm = ref({
  pageNo: 1,
  pageSize: 2,
  userId: '',
  nickName: ''
})

const onSubmit = () => {
  getUserListByServe()
}

const tableData = ref([])
const total = ref(0)
const small = ref(false)
const background = ref(true)
const disabled = ref(false)

const tableInit = () => {
  dialogTitle.value = '新增用户'
  newUser.value.userId = null
  newUser.value.userName = ''
  newUser.value.nickName = ''
  newUser.value.password = ''
  newUser.value.againPwd = ''
  dialogFormVisible.value = true;
}
// 销毁
// const tableDestroy = () => {
//   newUser.value = {
//     userId: null,
//     userName: '',
//     nickName: '',
//     password: '',
//     againPwd: ''
//   }
//   dialogFormVisible.value = true;
// }


// 点击修改按钮
const handleEdit = async (userId, row) => {
  let resp = await getUserById(userId)
  dialogTitle.value = '修改用户'
  newUser.value.userId = resp.data.userId
  newUser.value.userName = resp.data.userName
  newUser.value.nickName = resp.data.nickName
  newUser.value.password = resp.data.password
  newUser.value.againPwd = ''
  dialogFormVisible.value = true;
}

// 点击删除按钮
const handleDelete = (userId, row) => {
  ElMessageBox.confirm(
      '是否确认删除?',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
      }
  ).then(async () => {
    let resp = await deleteUser(userId)
    if (resp.status === 200) {
      ElMessage({type: 'success', message: '删除成功!'})
      await getUserListByServe()
    }
  }).catch(() => {
    ElMessage({type: 'error', message: '系统忙，请稍后重试!'})
  })

}

const getUserListByServe = async () => {
  let resp = await getUserList(queryForm.value)
  total.value = resp.data.total
  queryForm.value.pageSize = resp.data.pageSize
  tableData.value = resp.data.list
}

const handleSizeChange = (pageSize) => {
  queryForm.value.pageSize = pageSize
  getUserListByServe()
}
const handleCurrentChange = (pageNo) => {
  queryForm.value.pageNo = pageNo
  getUserListByServe()
}

onMounted(() => {
  getUserListByServe()
})

const saveUserSubmit = () => {

  // 表单校验
  if (newUser.value.againPwd !== newUser.value.password) {
    ElMessage({type: 'warning', message: '两次输入密码不一致!'})
    return
  }

  delete newUser.value.againPwd

  if (newUser.value.userId === null) {
    saveUser(newUser.value).then(resp => {
      if (resp.status === 200) {
        ElMessage({type: 'success', message: '添加成功!'})
      }
    }).catch(error => {
      ElMessage({type: 'error', message: '系统忙，请稍后重试!'})
    })
  } else {
    updateUser(newUser.value).then(resp => {
      if (resp.status === 200) {
        ElMessage({type: 'success', message: '修改成功!'})
      }
    }).catch(error => {
      ElMessage({type: 'error', message: '系统忙，请稍后重试!'})
    })
  }

  dialogFormVisible.value = false
  getUserListByServe()
  console.log('更新后')
  console.log(tableData.value)
}


</script>