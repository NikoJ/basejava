package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        Resume resume;
        switch (action) {
            case "add":
                resume = Resume.EMPTY;
                break;
            case "delete":
                storage.delete(uuid);
                resp.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = resume.getSections(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextBoxSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            CompanySection companySection = (CompanySection) resume.getSections(type);
                            List<Company> emptyCompany = new ArrayList<>();
                            emptyCompany.add(Company.EMPTY);
                            if (companySection != null) {
                                for (Company company : companySection.getCompanies()) {
                                    List<Role> emptyRole = new ArrayList<>();
                                    emptyRole.add(Role.EMPTY);
                                    emptyRole.addAll(company.getRoles());
                                    emptyCompany.add(new Company(company.getSitePage(), emptyRole));
                                }
                            }
                            section = new CompanySection(emptyCompany);
                            break;
                    }
                    resume.addSections(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("resume", resume);
        req.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        String fullName = req.getParameter("fullName");

        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume resume;
        if (isCreate) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.addContacts(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = req.getParameter(type.name());
            String[] values = req.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSections(type, new TextBoxSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSections(type, new ListSection(value.split("\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Company> companyList = new ArrayList<>();
                        String[] urls = req.getParameterValues(type.name() + "_url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Role> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = req.getParameterValues(pfx + "startDate");
                                String[] endDates = req.getParameterValues(pfx + "endDate");
                                String[] titles = req.getParameterValues(pfx + "name");
                                String[] descriptions = req.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Role(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                companyList.add(new Company(new Site(name, urls[i]), positions));
                            }
                        }
                        resume.addSections(type, new CompanySection(companyList));
                        break;
                }
            }
        }
        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        resp.sendRedirect("resume");
    }
}
