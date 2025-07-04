package com.CapstoneProject.PartnerFinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CapstoneProject.PartnerFinder.model.ProjectCategory;
import com.CapstoneProject.PartnerFinder.repo.ProjectCategoryRepository;


@Service
public class ProjectCategoryService {


    private ProjectCategoryRepository projectCategoryRepository;

    @Autowired
    public ProjectCategoryService(ProjectCategoryRepository projectCategoryRepository) {
        this.projectCategoryRepository = projectCategoryRepository;
    }

    public ProjectCategory createProjectCategory(ProjectCategory projectCategory) {

        return projectCategoryRepository.save(projectCategory);

    }

    public List<ProjectCategory> getAllProjectCategories() {

        return projectCategoryRepository.findAll();

    }

    public ProjectCategory updateProjectCategory(Long id, ProjectCategory projectCategory) throws Exception {
        ProjectCategory dbprojectCategory = isProjectCategoryExist(id);

        dbprojectCategory.setField(projectCategory.getField());
        return projectCategoryRepository.save(dbprojectCategory);
    }

    public String deleteProjectCategoryById(Long id) throws Exception {


        ProjectCategory dbprojectCategory = isProjectCategoryExist(id);

        projectCategoryRepository.delete(dbprojectCategory);

        return "Project Category Deleted Successfully";
    }


    private ProjectCategory isProjectCategoryExist(Long id) throws Exception {
        ProjectCategory dbprojectCategory = projectCategoryRepository.findById(id).orElseThrow(
                () -> new Exception("Project Category Not Found"));

        return dbprojectCategory;
    }

}
