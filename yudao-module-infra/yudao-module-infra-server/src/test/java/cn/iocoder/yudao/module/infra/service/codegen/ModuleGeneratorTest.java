
package cn.iocoder.yudao.module.infra.service.codegen;

import cn.iocoder.yudao.module.infra.service.codegen.inner.ModuleGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 模块生成器测试
 * 
 * @author 芋道源码
 */
public class ModuleGeneratorTest {

    @Test
    public void testGenerateModule() throws Exception {
        // 使用项目根目录作为输出目录（修改这里来改变输出位置）
        String projectRoot = System.getProperty("user.dir");

        String moduleName = "points";

        // 模块目录
        String moduleDir = projectRoot + "/yudao-module-"+moduleName;
        File moduleDirFile = new File(moduleDir);
        if (moduleDirFile.exists()) {
            // 删除旧的模块目录
            deleteDirectory(moduleDirFile);
        }

        // 创建生成器
        ModuleGenerator generator = new ModuleGenerator();

        // 创建配置 - 使用 order 模块
        ModuleGenerator.ModuleConfig config = new ModuleGenerator.ModuleConfig( moduleName , "积分模块")
                .port(48099)
                .generateDockerfile(true)
                .generateTest(true);

        // 生成模块到项目根目录
        generator.generate(config, projectRoot);

        // 验证生成的文件结构
        assertTrue(new File(moduleDir + "/pom.xml").exists(), "根模块 pom.xml 不存在");

        // API 模块
        String apiDir = moduleDir + "/yudao-module-"+ moduleName +"-api";
        assertTrue(new File(apiDir + "/pom.xml").exists(), "API 模块 pom.xml 不存在");
        assertTrue(new File(apiDir + "/src/main/java/cn/iocoder/yudao/module/"+moduleName+"/package-info.java").exists(),
                "API 模块 package-info.java 不存在");
        assertTrue(new File(apiDir + "/src/main/java/cn/iocoder/yudao/module/"+moduleName+"/enums/ErrorCodeConstants.java").exists(),
                "API 模块 ErrorCodeConstants.java 不存在");

        // Server 模块
        String serverDir = moduleDir + "/yudao-module-"+ moduleName +"-server";
        assertTrue(new File(serverDir + "/pom.xml").exists(), "Server 模块 pom.xml 不存在");
        assertTrue(new File(serverDir + "/Dockerfile").exists(), "Server 模块 Dockerfile 不存在");
        assertTrue(new File(serverDir + "/src/main/java/cn/iocoder/yudao/module/"+ moduleName +"/PointsServerApplication.java").exists(),
                "Server 模块启动类不存在");
        assertTrue(new File(serverDir + "/src/main/resources/application.yaml").exists(),
                "Server 模块 application.yaml 不存在");
        assertTrue(new File(serverDir + "/src/main/resources/application-dev.yaml").exists(),
                "Server 模块 application-dev.yaml 不存在");
        assertTrue(new File(serverDir + "/src/main/resources/application-local.yaml").exists(),
                "Server 模块 application-local.yaml 不存在");
        assertTrue(new File(serverDir + "/src/main/resources/logback-spring.xml").exists(),
                "Server 模块 logback-spring.xml 不存在");
        assertTrue(new File(serverDir + "/src/test/resources/application-unit-test.yaml").exists(),
                "Server 模块测试配置不存在");

        // 验证 application.yaml 内容
        String appYamlContent = new String(java.nio.file.Files.readAllBytes(
                java.nio.file.Paths.get(serverDir + "/src/main/resources/application.yaml")));
        assertTrue(appYamlContent.contains("name: "+ moduleName +"-server"), "application.yaml 不包含正确的应用名称");
        assertTrue(appYamlContent.contains("port: 48099"), "application.yaml 不包含正确的端口");
        assertTrue(appYamlContent.contains("base-package: cn.iocoder.yudao.module."+ moduleName),
                "application.yaml 不包含正确的包路径");

        System.out.println("模块生成成功！");
        System.out.println("输出目录: " + moduleDir);
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }
}
