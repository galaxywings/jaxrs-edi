export default {
  props: {
    item: {
      // would be an object of {
      //   "key": "xxx"
      //   "value": {
      //     "seq": 0,
      //     "content_type": 0,
      //     "object_id": 0,
      //     "id": 0, // could be omitted in the ajax
      //     "process": 0, // could omitted in the ajax
      //   },
      //   "schema": {
      //     "code": "xxx"
      //     "name": "xxx",
      //     "extra_schema": {...}
      //   },
      //   "content_type": {
      //     "id": 0,
      //     "app_label": "xxx",
      //     "model": "xxx"
      //   },
      //   "content_object": {...}
      // }
      type: Object,
      require: true
    }
  },
  methods: {
    validate (rule, value, callback) {
      if (!value.value.object_id) {
        callback(new Error('请选择相关服务实例'))
      }
      callback()
    },
    save () {
      // default return this step's value
      return new Promise((resolve, reject) => {
        resolve(this.item.value)
      })
    },
    handleContentChange (contentObject) {
      this.item.content_object = contentObject
    }
  }
}
