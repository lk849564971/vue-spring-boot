module.exports = {
    devServer: {host:"localhost",
      port:8080,
      https:false,
      open:true,
      proxy:"http://localhost:2048"

    }
    }
