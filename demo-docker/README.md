# docker 部署 Springboot

## idea 中直接部署到 docker 中

将编写好的代码使用 maven package 生成 jar 包

![image-20230103192924018](assets/image-20230103192924018.png)

然后配置 idea 

![image-20230103193105438](assets/image-20230103193105438.png)

随后就可以运行 Dockerfile  了

## Linux 中部署到 docker 中

```dockerfile
docker build .
```

