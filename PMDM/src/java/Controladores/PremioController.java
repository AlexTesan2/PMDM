package Controladores;

import Modelos.Premio;
import Controladores.util.JsfUtil;
import Controladores.util.PaginationHelper;
import Modelos.Libro;
import Repositorios.PremioFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("premioController")
@SessionScoped
public class PremioController implements Serializable {


    private Premio current;
    private DataModel items = null; //el atributo del que hablamos, nadie me impide crear más
    @EJB
    private Repositorios.PremioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PremioController() {
    }

    public Premio getSelected() {
        if (current == null) {
            current = new Premio();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PremioFacade getFacade() {
        return ejbFacade;
    }
    //con la linea comentada elegimos si sacar los elementos de 10 en 10 o todos
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(getItemsAvailableSelectMany().length) {
            //pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Premio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Premio();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PremioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Premio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PremioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Premio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PremioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }
    //este método devolverá un vector con todos los premios que hay
    public SelectItem[] getItemsAvailableSelectMany() {
        //return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        return getSelectPremio(ejbFacade.premiosOrdenados(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        //return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        return getSelectPremio(ejbFacade.premiosOrdenados(), true);
    }
    
    public SelectItem[] getItemsPorAutor() {
        //return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        return getSelectPremio(ejbFacade.premiosAutorOrdenados(), true);
    }
    public SelectItem[] getItemsPorLibro() {
        //return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        return getSelectPremio(ejbFacade.premiosLibroOrdenados(), false);
    }
    
    
    

    public Premio getPremio(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Premio.class, value="premiosConverter")
    public static class PremioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PremioController controller = (PremioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "premioController");
            return controller.getPremio(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Premio) {
                Premio o = (Premio) object;
                return getStringKey(o.getCodPremio());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Premio.class.getName());
            }
        }

    }
    public static SelectItem[] getSelectPremio(List<Premio> entities, boolean selectOne) {
        //int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[entities.size()];
        int i = 0;
        /*if (selectOne) {
            items[0] = new SelectItem("0", "Elige un premio");
            i++;
        }//nombre del objeto : y la lista, todos los objetos del mundo mundial son de la superclase Object*/
        for (Premio x : entities) {
            items[i++] = new SelectItem(x, x.getNomPremio());
        }
        return items;
    }

}
