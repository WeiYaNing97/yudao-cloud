
package cn.iocoder.yudao.module.infra.service.codegen.inner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 模块脚手架生成器
 * 
 * 用于生成完整的新模块结构，包括：
 * - 根模块 pom.xml
 * - api 模块（yudao-module-{moduleName}-api）
 * - server 模块（yudao-module-{moduleName}-server）
 * 
 * @author 芋道源码
 */
@Component
public class ModuleGenerator {

    /**
     * 生成模块配置
     */
    public static class ModuleConfig {
        /**
         * 模块名称（小写，如：crm、erp、iot）
         */
        private String moduleName;
        
        /**
         * 模块描述
         */
        private String description;
        
        /**
         * 父包路径（默认：cn.iocoder.yudao.module）
         */
        private String basePackage = "cn.iocoder.yudao.module";
        
        /**
         * 服务端口（默认：48080 + 模块序号）
         */
        private Integer port;
        
        /**
         * 是否生成 Dockerfile
         */
        private boolean generateDockerfile = true;
        
        /**
         * 是否生成测试目录
         */
        private boolean generateTest = true;

        public ModuleConfig(String moduleName, String description) {
            this.moduleName = moduleName;
            this.description = description;
        }

        public ModuleConfig moduleName(String moduleName) {
            this.moduleName = moduleName;
            return this;
        }

        public ModuleConfig description(String description) {
            this.description = description;
            return this;
        }

        public ModuleConfig basePackage(String basePackage) {
            this.basePackage = basePackage;
            return this;
        }

        public ModuleConfig port(Integer port) {
            this.port = port;
            return this;
        }

        public ModuleConfig generateDockerfile(boolean generateDockerfile) {
            this.generateDockerfile = generateDockerfile;
            return this;
        }

        public ModuleConfig generateTest(boolean generateTest) {
            this.generateTest = generateTest;
            return this;
        }

        public String getModuleName() { return moduleName; }
        public String getDescription() { return description; }
        public String getBasePackage() { return basePackage; }
        public Integer getPort() { return port; }
        public boolean isGenerateDockerfile() { return generateDockerfile; }
        public boolean isGenerateTest() { return generateTest; }
        
        /**
         * 获取模块大驼峰名称（如：Crm、Erp）
         */
        public String getModuleNameUpper() {
            return StrUtil.upperFirst(moduleName);
        }
        
        /**
         * 获取完整包路径（如：cn.iocoder.yudao.module.crm）
         */
        public String getFullPackage() {
            return basePackage + "." + moduleName;
        }
        
        /**
         * 获取包路径的文件分隔符形式（如：cn/iocoder/yudao/module/crm）
         */
        public String getPackagePath() {
            return getFullPackage().replace(".", "/");
        }
        
        /**
         * 获取模块根目录名称（如：yudao-module-crm）
         */
        public String getModuleDirName() {
            return "yudao-module-" + moduleName;
        }
        
        /**
         * 获取 API 模块目录名称（如：yudao-module-crm-api）
         */
        public String getApiModuleDirName() {
            return getModuleDirName() + "-api";
        }
        
        /**
         * 获取 Server 模块目录名称（如：yudao-module-crm-server）
         */
        public String getServerModuleDirName() {
            return getModuleDirName() + "-server";
        }
        
        /**
         * 获取启动类名称（如：CrmServerApplication）
         */
        public String getApplicationClassName() {
            return getModuleNameUpper() + "ServerApplication";
        }
        
        /**
         * 获取应用名称（如：crm-server）
         */
        public String getApplicationName() {
            return moduleName + "-server";
        }
    }

    /**
     * 生成模块
     * 
     * @param config 模块配置
     * @param outputDir 输出目录（项目根目录）
     */
    public void generate(ModuleConfig config, String outputDir) throws IOException {
        String moduleDir = outputDir + File.separator + config.getModuleDirName();
        
        // 1. 创建模块根目录
        FileUtil.mkdir(moduleDir);
        
        // 2. 生成根模块 pom.xml
        generateRootPom(config, moduleDir);
        
        // 3. 生成 API 模块
        generateApiModule(config, moduleDir);
        
        // 4. 生成 Server 模块
        generateServerModule(config, moduleDir);
        
        // 5. 更新根项目的 pom.xml
        updateRootPom(outputDir, config);
        
        // 6. 更新 yudao-server 的 pom.xml（可选）
        updateYudaoServerPom(outputDir, config);
    }

    /**
     * 生成根模块 pom.xml
     */
    private void generateRootPom(ModuleConfig config, String moduleDir) throws IOException {
        String content = String.format(ROOT_POM_TEMPLATE,
                config.getModuleName(),
                config.getModuleName(),
                config.getModuleName(),
                config.getDescription());
        FileUtil.writeString(content, moduleDir + "/pom.xml", StandardCharsets.UTF_8);
    }

    /**
     * 生成 API 模块
     */
    private void generateApiModule(ModuleConfig config, String moduleDir) throws IOException {
        String apiDir = moduleDir + "/" + config.getApiModuleDirName();
        FileUtil.mkdir(apiDir);
        
        // pom.xml
        String pomContent = String.format(API_POM_TEMPLATE,
                config.getModuleName(),
                config.getModuleName(),
                config.getModuleName());
        FileUtil.writeString(pomContent, apiDir + "/pom.xml", StandardCharsets.UTF_8);
        
        // 创建 api 目录结构
        String apiJavaDir = apiDir + "/src/main/java/" + config.getPackagePath();
        FileUtil.mkdir(apiJavaDir);
        FileUtil.writeString("", apiJavaDir + "/package-info.java", StandardCharsets.UTF_8);
        
        // 创建 enums 目录
        FileUtil.mkdir(apiJavaDir + "/enums");
        FileUtil.writeString(String.format(ERROR_CODE_CONSTANTS_TEMPLATE,
                config.getModuleName(),
                config.getModuleNameUpper(),
                config.getModuleNameUpper()), 
                apiJavaDir + "/enums/ErrorCodeConstants.java", StandardCharsets.UTF_8);
    }

    /**
     * 生成 Server 模块
     */
    private void generateServerModule(ModuleConfig config, String moduleDir) throws IOException {
        String serverDir = moduleDir + "/" + config.getServerModuleDirName();
        FileUtil.mkdir(serverDir);
        
        // pom.xml
        String pomContent = String.format(SERVER_POM_TEMPLATE,
                config.getModuleName(),
                config.getModuleName(),
                config.getModuleName(),
                config.getModuleName());
        FileUtil.writeString(pomContent, serverDir + "/pom.xml", StandardCharsets.UTF_8);
        
        // Dockerfile
        if (config.isGenerateDockerfile()) {
            FileUtil.writeString(String.format(DOCKERFILE_TEMPLATE,
                    config.getServerModuleDirName()),
                    serverDir + "/Dockerfile", StandardCharsets.UTF_8);
        }
        
        // Java 目录
        String javaDir = serverDir + "/src/main/java/" + config.getPackagePath();
        FileUtil.mkdir(javaDir);
        
        // 启动类
        FileUtil.writeString(String.format(APPLICATION_TEMPLATE,
                config.getFullPackage(),
                config.getApplicationClassName(),
                config.getApplicationClassName()),
                javaDir + "/" + config.getApplicationClassName() + ".java", StandardCharsets.UTF_8);
        
        // package-info.java
        FileUtil.writeString("", javaDir + "/package-info.java", StandardCharsets.UTF_8);
        
        // resources 目录
        String resourcesDir = serverDir + "/src/main/resources";
        FileUtil.mkdir(resourcesDir);
        
        // application.yaml
        Integer port = config.getPort() != null ? config.getPort() : 48080;
        String appYamlContent = String.format(APPLICATION_YAML_TEMPLATE,
                config.getApplicationName(),
                port,
                config.getFullPackage());
        FileUtil.writeString(appYamlContent, resourcesDir + "/application.yaml", StandardCharsets.UTF_8);
        
        // application-dev.yaml
        FileUtil.writeString(APPLICATION_DEV_YAML_TEMPLATE, 
                resourcesDir + "/application-dev.yaml", StandardCharsets.UTF_8);
        
        // application-local.yaml
        FileUtil.writeString(APPLICATION_LOCAL_YAML_TEMPLATE, 
                resourcesDir + "/application-local.yaml", StandardCharsets.UTF_8);
        
        // logback-spring.xml
        FileUtil.writeString(LOGBACK_TEMPLATE, 
                resourcesDir + "/logback-spring.xml", StandardCharsets.UTF_8);
        
        // 测试目录
        if (config.isGenerateTest()) {
            String testResourcesDir = serverDir + "/src/test/resources";
            FileUtil.mkdir(testResourcesDir);
            FileUtil.writeString(TEST_APPLICATION_TEMPLATE,
                    testResourcesDir + "/application-unit-test.yaml", StandardCharsets.UTF_8);
        }
    }

    /**
     * 更新根项目的 pom.xml
     */
    private void updateRootPom(String outputDir, ModuleConfig config) throws IOException {
        String pomPath = outputDir + "/pom.xml";
        if (!FileUtil.exist(pomPath)) {
            // 如果根项目 pom.xml 不存在，跳过更新
            return;
        }
        String content = FileUtil.readString(pomPath, StandardCharsets.UTF_8);
        
        // 在 </modules> 之前添加新模块
        String moduleTag = "<module>" + config.getModuleDirName() + "</module>";
        content = content.replace("</modules>", "    " + moduleTag + "\n</modules>");
        
        FileUtil.writeString(content, pomPath, StandardCharsets.UTF_8);
    }

    /**
     * 更新 yudao-server 的 pom.xml
     */
    private void updateYudaoServerPom(String outputDir, ModuleConfig config) throws IOException {
        String pomPath = outputDir + "/yudao-server/pom.xml";
        if (!FileUtil.exist(pomPath)) {
            return;
        }
        
        String content = FileUtil.readString(pomPath, StandardCharsets.UTF_8);
        
        // 在 </dependencies> 之前添加新模块依赖（注释形式）
        String dependency = String.format(SERVER_DEPENDENCY_TEMPLATE,
                config.getServerModuleDirName());
        
        // 找到 </dependencies> 标签之前插入
        content = content.replace("</dependencies>", 
                "<!--        <dependency>\n" +
                "            <groupId>cn.iocoder.cloud</groupId>\n" +
                "            <artifactId>" + config.getServerModuleDirName() + "</artifactId>\n" +
                "        </dependency>-->\n" +
                "</dependencies>");
        
        FileUtil.writeString(content, pomPath, StandardCharsets.UTF_8);
    }

    // ========== 模板常量 ==========

    private static final String ROOT_POM_TEMPLATE = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
        "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
        "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
        "    <parent>\n" +
        "        <groupId>cn.iocoder.cloud</groupId>\n" +
        "        <artifactId>yudao</artifactId>\n" +
        "        <version>${revision}</version>\n" +
        "    </parent>\n" +
        "    <modelVersion>4.0.0</modelVersion>\n" +
        "    <modules>\n" +
        "        <module>yudao-module-%s-api</module>\n" +
        "        <module>yudao-module-%s-server</module>\n" +
        "    </modules>\n" +
        "    <artifactId>yudao-module-%s</artifactId>\n" +
        "    <packaging>pom</packaging>\n" +
        "\n" +
        "    <name>${project.artifactId}</name>\n" +
        "    <description>\n" +
        "        %s\n" +
        "    </description>\n" +
        "\n" +
        "</project>";

    private static final String API_POM_TEMPLATE = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
        "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
        "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
        "    <parent>\n" +
        "        <groupId>cn.iocoder.cloud</groupId>\n" +
        "        <artifactId>yudao-module-%s</artifactId>\n" +
        "        <version>${revision}</version>\n" +
        "    </parent>\n" +
        "    <modelVersion>4.0.0</modelVersion>\n" +
        "    <artifactId>yudao-module-%s-api</artifactId>\n" +
        "    <packaging>jar</packaging>\n" +
        "\n" +
        "    <name>${project.artifactId}</name>\n" +
        "    <description>\n" +
        "        %s 模块 API，暴露给其它模块调用\n" +
        "    </description>\n" +
        "\n" +
        "    <dependencies>\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-common</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- Web 相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>org.springdoc</groupId>\n" +
        "            <artifactId>springdoc-openapi-ui</artifactId>\n" +
        "            <scope>provided</scope>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- 参数校验 -->\n" +
        "        <dependency>\n" +
        "            <groupId>org.springframework.boot</groupId>\n" +
        "            <artifactId>spring-boot-starter-validation</artifactId>\n" +
        "            <optional>true</optional>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- RPC 远程调用相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>org.springframework.cloud</groupId>\n" +
        "            <artifactId>spring-cloud-starter-openfeign</artifactId>\n" +
        "            <optional>true</optional>\n" +
        "        </dependency>\n" +
        "    </dependencies>\n" +
        "\n" +
        "</project>";

    private static final String SERVER_POM_TEMPLATE = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
        "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
        "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
        "    <parent>\n" +
        "        <groupId>cn.iocoder.cloud</groupId>\n" +
        "        <artifactId>yudao-module-%s</artifactId>\n" +
        "        <version>${revision}</version>\n" +
        "    </parent>\n" +
        "    <modelVersion>4.0.0</modelVersion>\n" +
        "    <artifactId>yudao-module-%s-server</artifactId>\n" +
        "    <packaging>jar</packaging>\n" +
        "\n" +
        "    <name>${project.artifactId}</name>\n" +
        "    <description>\n" +
        "        %s 模块服务端实现\n" +
        "    </description>\n" +
        "\n" +
        "    <dependencies>\n" +
        "        <!-- Spring Cloud 基础 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-env</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- 依赖服务 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-module-%s-api</artifactId>\n" +
        "            <version>${revision}</version>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- 业务组件 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-biz-tenant</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- Web 相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-security</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- DB 相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-mybatis</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-redis</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- RPC 远程调用相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-rpc</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- Registry 注册中心相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>com.alibaba.cloud</groupId>\n" +
        "            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- Config 配置中心相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>com.alibaba.cloud</groupId>\n" +
        "            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- Job 定时任务相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-job</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- 消息队列相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-mq</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- Test 测试相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-test</artifactId>\n" +
        "            <scope>test</scope>\n" +
        "        </dependency>\n" +
        "\n" +
        "        <!-- 监控相关 -->\n" +
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>yudao-spring-boot-starter-monitor</artifactId>\n" +
        "        </dependency>\n" +
        "\n" +
        "    </dependencies>\n" +
        "    <build>\n" +
        "        <!-- 设置构建的 jar 包名 -->\n" +
        "        <finalName>${project.artifactId}</finalName>\n" +
        "        <plugins>\n" +
        "            <!-- 打包 -->\n" +
        "            <plugin>\n" +
        "                <groupId>org.springframework.boot</groupId>\n" +
        "                <artifactId>spring-boot-maven-plugin</artifactId>\n" +
        "                <version>${spring.boot.version}</version>\n" +
        "                <executions>\n" +
        "                    <execution>\n" +
        "                        <goals>\n" +
        "                            <goal>repackage</goal>\n" +
        "                        </goals>\n" +
        "                    </execution>\n" +
        "                </executions>\n" +
        "            </plugin>\n" +
        "        </plugins>\n" +
        "    </build>\n" +
        "\n" +
        "</project>";

    private static final String DOCKERFILE_TEMPLATE = 
        "FROM openjdk:8-jre-alpine\n" +
        "\n" +
        "MAINTAINER 芋道源码 <admin@iocoder.cn>\n" +
        "\n" +
        "# 设置时区\n" +
        "RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime\n" +
        "RUN echo 'Asia/Shanghai' > /etc/timezone\n" +
        "\n" +
        "# 创建工作目录\n" +
        "WORKDIR /app\n" +
        "\n" +
        "# 复制 jar 包\n" +
        "COPY %s.jar /app/app.jar\n" +
        "\n" +
        "# 启动命令\n" +
        "ENTRYPOINT [\"java\", \"-jar\", \"app.jar\"]";

    private static final String APPLICATION_TEMPLATE = 
        "package %s;\n" +
        "\n" +
        "import org.springframework.boot.SpringApplication;\n" +
        "import org.springframework.boot.autoconfigure.SpringBootApplication;\n" +
        "\n" +
        "/**\n" +
        " * 项目的启动类\n" +
        " * <p>\n" +
        " * 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章\n" +
        " *\n" +
        " * @author 芋道源码\n" +
        " */\n" +
        "@SpringBootApplication\n" +
        "public class %s {\n" +
        "\n" +
        "    public static void main(String[] args) {\n" +
        "        SpringApplication.run(%s.class, args);\n" +
        "    }\n" +
        "\n" +
        "}";

    private static final String APPLICATION_YAML_TEMPLATE = 
        "spring:\n" +
        "  application:\n" +
        "    name: %s\n" +
        "\n" +
        "  profiles:\n" +
        "    active: local\n" +
        "\n" +
        "  main:\n" +
        "    allow-circular-references: true\n" +
        "    allow-bean-definition-overriding: true\n" +
        "\n" +
        "  config:\n" +
        "    import:\n" +
        "      - optional:classpath:application-${spring.profiles.active}.yaml\n" +
        "      - optional:nacos:${spring.application.name}-${spring.profiles.active}.yaml\n" +
        "\n" +
        "  servlet:\n" +
        "    multipart:\n" +
        "      max-file-size: 16MB\n" +
        "      max-request-size: 32MB\n" +
        "\n" +
        "  jackson:\n" +
        "    serialization:\n" +
        "      write-dates-as-timestamps: true\n" +
        "      write-date-timestamps-as-nanoseconds: false\n" +
        "      write-durations-as-timestamps: true\n" +
        "      fail-on-empty-beans: false\n" +
        "\n" +
        "  cache:\n" +
        "    type: REDIS\n" +
        "    redis:\n" +
        "      time-to-live: 1h\n" +
        "\n" +
        "server:\n" +
        "  port: %d\n" +
        "\n" +
        "logging:\n" +
        "  file:\n" +
        "    name: ${user.home}/logs/${spring.application.name}.log\n" +
        "\n" +
        "springdoc:\n" +
        "  api-docs:\n" +
        "    enabled: true\n" +
        "    path: /v3/api-docs\n" +
        "  swagger-ui:\n" +
        "    enabled: true\n" +
        "    path: /swagger-ui\n" +
        "  default-flat-param-object: true\n" +
        "\n" +
        "knife4j:\n" +
        "  enable: true\n" +
        "  setting:\n" +
        "    language: zh_cn\n" +
        "\n" +
        "mybatis-plus:\n" +
        "  configuration:\n" +
        "    map-underscore-to-camel-case: true\n" +
        "  global-config:\n" +
        "    db-config:\n" +
        "      id-type: NONE\n" +
        "      logic-delete-value: 1\n" +
        "      logic-not-delete-value: 0\n" +
        "    banner: false\n" +
        "  type-aliases-package: ${yudao.info.base-package}.dal.dataobject\n" +
        "  encryptor:\n" +
        "    password: XDV71a+xqStEA3WH\n" +
        "\n" +
        "mybatis-plus-join:\n" +
        "  banner: false\n" +
        "\n" +
        "spring:\n" +
        "  data:\n" +
        "    redis:\n" +
        "      repositories:\n" +
        "        enabled: false\n" +
        "\n" +
        "easy-trans:\n" +
        "  is-enable-global: false\n" +
        "\n" +
        "xxl:\n" +
        "  job:\n" +
        "    executor:\n" +
        "      appname: ${spring.application.name}\n" +
        "      logpath: ${user.home}/logs/xxl-job/${spring.application.name}\n" +
        "    accessToken: default_token\n" +
        "\n" +
        "yudao:\n" +
        "  info:\n" +
        "    version: 1.0.0\n" +
        "    base-package: %s\n" +
        "  web:\n" +
        "    admin-ui:\n" +
        "      url: http://dashboard.yudao.iocoder.cn\n" +
        "  xss:\n" +
        "    enable: false\n" +
        "    exclude-urls:\n" +
        "      - ${spring.boot.admin.context-path}/**\n" +
        "      - ${management.endpoints.web.base-path}/**\n" +
        "  swagger:\n" +
        "    title: 管理后台\n" +
        "    description: 提供管理员管理的所有功能\n" +
        "    version: ${yudao.info.version}\n" +
        "  tenant:\n" +
        "    enable: true\n" +
        "\n" +
        "debug: false";

    private static final String APPLICATION_DEV_YAML_TEMPLATE = 
        "# ========== 数据库配置 ==========\n" +
        "spring:\n" +
        "  datasource:\n" +
        "    dynamic:\n" +
        "      datasource:\n" +
        "        master:\n" +
        "          url: jdbc:mysql://localhost:3306/example_db?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\n" +
        "          username: root\n" +
        "          password: password\n" +
        "\n" +
        "# ========== Nacos 配置 ==========\n" +
        "spring:\n" +
        "  cloud:\n" +
        "    nacos:\n" +
        "      discovery:\n" +
        "        server-addr: localhost:8848\n" +
        "        namespace: example\n" +
        "      config:\n" +
        "        server-addr: localhost:8848\n" +
        "        namespace: example\n" +
        "\n" +
        "# ========== Redis 配置 ==========\n" +
        "spring:\n" +
        "  redis:\n" +
        "    host: localhost\n" +
        "    port: 6379\n" +
        "\n" +
        "# ========== XXL-Job 配置 ==========\n" +
        "xxl:\n" +
        "  job:\n" +
        "    admin:\n" +
        "      addresses: http://localhost:8080/xxl-job-admin";

    private static final String APPLICATION_LOCAL_YAML_TEMPLATE = 
        "# ========== 数据库配置 ==========\n" +
        "spring:\n" +
        "  datasource:\n" +
        "    dynamic:\n" +
        "      datasource:\n" +
        "        master:\n" +
        "          url: jdbc:mysql://localhost:3306/example_db?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\n" +
        "          username: root\n" +
        "          password: password\n" +
        "\n" +
        "# ========== Nacos 配置 ==========\n" +
        "spring:\n" +
        "  cloud:\n" +
        "    nacos:\n" +
        "      discovery:\n" +
        "        server-addr: localhost:8848\n" +
        "        namespace: local\n" +
        "      config:\n" +
        "        server-addr: localhost:8848\n" +
        "        namespace: local\n" +
        "\n" +
        "# ========== Redis 配置 ==========\n" +
        "spring:\n" +
        "  redis:\n" +
        "    host: localhost\n" +
        "    port: 6379\n" +
        "\n" +
        "# ========== XXL-Job 配置 ==========\n" +
        "xxl:\n" +
        "  job:\n" +
        "    admin:\n" +
        "      addresses: http://localhost:8080/xxl-job-admin";

    private static final String LOGBACK_TEMPLATE = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<configuration scan=\"true\" scanPeriod=\"60 seconds\">\n" +
        "\n" +
        "    <property name=\"LOG_PATH\" value=\"${user.home}/logs/${spring.application.name}\"/>\n" +
        "    <property name=\"APP_NAME\" value=\"${spring.application.name}\"/>\n" +
        "\n" +
        "    <appender name=\"CONSOLE\" class=\"ch.qos.logback.core.ConsoleAppender\">\n" +
        "        <encoder>\n" +
        "            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>\n" +
        "            <charset>UTF-8</charset>\n" +
        "        </encoder>\n" +
        "    </appender>\n" +
        "\n" +
        "    <appender name=\"FILE\" class=\"ch.qos.logback.core.rolling.RollingFileAppender\">\n" +
        "        <file>${LOG_PATH}/${APP_NAME}.log</file>\n" +
        "        <rollingPolicy class=\"ch.qos.logback.core.rolling.TimeBasedRollingPolicy\">\n" +
        "            <fileNamePattern>${LOG_PATH}/${APP_NAME}.%d{yyyy-MM-dd}.log</fileNamePattern>\n" +
        "            <maxHistory>30</maxHistory>\n" +
        "        </rollingPolicy>\n" +
        "        <encoder>\n" +
        "            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>\n" +
        "            <charset>UTF-8</charset>\n" +
        "        </encoder>\n" +
        "    </appender>\n" +
        "\n" +
        "    <logger name=\"cn.iocoder\" level=\"DEBUG\" additivity=\"false\">\n" +
        "        <appender-ref ref=\"CONSOLE\"/>\n" +
        "        <appender-ref ref=\"FILE\"/>\n" +
        "    </logger>\n" +
        "\n" +
        "    <root level=\"INFO\">\n" +
        "        <appender-ref ref=\"CONSOLE\"/>\n" +
        "        <appender-ref ref=\"FILE\"/>\n" +
        "    </root>\n" +
        "\n" +
        "</configuration>";

    private static final String TEST_APPLICATION_TEMPLATE = 
        "spring:\n" +
        "  datasource:\n" +
        "    dynamic:\n" +
        "      datasource:\n" +
        "        master:\n" +
        "          url: jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE\n" +
        "          username: sa\n" +
        "          password:\n" +
        "          driver-class-name: org.h2.Driver\n" +
        "\n" +
        "# 禁用 Nacos\n" +
        "spring:\n" +
        "  cloud:\n" +
        "    nacos:\n" +
        "      discovery:\n" +
        "        enabled: false\n" +
        "      config:\n" +
        "        enabled: false\n" +
        "\n" +
        "# 禁用 Redis\n" +
        "spring:\n" +
        "  redis:\n" +
        "    host: localhost\n" +
        "    port: 6379\n" +
        "\n" +
        "# 禁用 XXL-Job\n" +
        "xxl:\n" +
        "  job:\n" +
        "    executor:\n" +
        "      enabled: false";

    private static final String ERROR_CODE_CONSTANTS_TEMPLATE = 
        "package cn.iocoder.yudao.module.%s.enums;\n" +
        "\n" +
        "import cn.iocoder.yudao.framework.common.exception.ErrorCode;\n" +
        "\n" +
        "/**\n" +
        " * %s 模块错误码\n" +
        " */\n" +
        "public interface ErrorCodeConstants {\n" +
        "\n" +
        "    // ========== %s 模块 1-002-000-000 ==========\n" +
        "\n" +
        "}\n";

    private static final String SERVER_DEPENDENCY_TEMPLATE = 
        "        <dependency>\n" +
        "            <groupId>cn.iocoder.cloud</groupId>\n" +
        "            <artifactId>%s</artifactId>\n" +
        "        </dependency>\n";

}
