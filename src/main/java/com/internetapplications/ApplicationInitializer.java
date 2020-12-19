package com.internetapplications;

import com.google.common.base.CaseFormat;
import com.internetapplications.configuration.InternetApplicationsProperties;
import com.internetapplications.entity.ApiPermission;
import com.internetapplications.entity.User;
import com.internetapplications.repository.ApiPermissionRepository;
import com.internetapplications.repository.UserRepository;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final ApiPermissionRepository apiPermissionRepository;
    private final UserRepository userRepository;
    private final InternetApplicationsProperties internetApplicationsProperties;

    public ApplicationInitializer(ApiPermissionRepository apiPermissionRepository, UserRepository userRepository,
                                  InternetApplicationsProperties internetApplicationsProperties) {
        this.apiPermissionRepository = apiPermissionRepository;
        this.userRepository = userRepository;
        this.internetApplicationsProperties = internetApplicationsProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        this.initPermissions();
    }

    private void initPermissions() {

        // 1- Add admin user
        addAdminUser();

        // 2- Init permissions
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        scanner.findCandidateComponents("com.internetapplications.controller")
                .stream()
                .map(BeanDefinition::getBeanClassName)
                .forEachOrdered(className -> {
                    try {
                        Class<?> cls = Class.forName(className);
                        RequestMapping requestMapping = (RequestMapping) cls.getAnnotation(RequestMapping.class);
                        String controllerPath = requestMapping.path() != null && requestMapping.path().length != 0 ? requestMapping.path()[0] : requestMapping.value()[0];
                        Method[] methods = cls.getMethods();
                        Arrays.stream(methods)
                                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                                .forEach(method -> {
                                    RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                                    String methodPath = methodRequestMapping.path() != null && methodRequestMapping.path().length != 0 ? methodRequestMapping.path()[0] : methodRequestMapping.value()[0];
                                    addPermission(cls.getSimpleName(), method.getName(), controllerPath + methodPath);
                                });
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                });
    }

    private void addPermission(String controllerName, String methodName, String link) {
        String code = controllerName.toUpperCase().replace("CONTROLLER", "") + "_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, methodName);
        if(apiPermissionRepository.findByCode(code) == null) {
            ApiPermission apiPermission = new ApiPermission(code, code.replace("_", " "), null, link);
            apiPermissionRepository.save(apiPermission);
        }
    }

    private void addAdminUser() {
        User user = userRepository.findByEmail("admin@internetapplications.com");
        if(user == null) {
            user = new User();
            user.setEmail("admin@internetapplications.com");
            user.setFirstName("Internet Applications");
            user.setLastName("Admin");
            user.setAdmin(true);
            user.setPassword(internetApplicationsProperties.getAdminDefaultPassword());
            userRepository.save(user);
        }
    }
}
