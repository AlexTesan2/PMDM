/*Aquí va la programación de la web
La vista le pedirá cosas a esta Clase, por ejemplo una lista de autores;
pero la lista estará en la base de datos, para acceder a ella, que 
está relacionada con los modelos (con consultas SQL)
lo haremos a traves de las funciones que se encuentran en los repositorios
y luego le pasamos todo a la vista para que lo muestre haciendo magia
*/


package Controladores;

import Modelos.Autor;
import Controladores.util.JsfUtil;
import Controladores.util.PaginationHelper;
import Modelos.AutorPremio;
import Modelos.Libro;
import Modelos.Premio;
import Repositorios.AutorFacade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javax.xml.bind.annotation.XmlTransient;

@Named("autorController")
@SessionScoped
public class AutorController implements Serializable {

    private Autor current;
    private DataModel items = null;
    @EJB
    private Repositorios.AutorFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Premio premio;
    private List<AutorPremio> lista;
    private Autor autor;
    private List<Libro> listalibros;
    private List<AutorPremio> listaPremios;
    
    
    public List<AutorPremio> getListaPremios() {
        return listaPremios;
    }

    public void setListaPremios(List<AutorPremio> listaPremios) {
        this.listaPremios = listaPremios;
    }

    public List<Libro> getListalibros() {
        return listalibros;
    }

    public void setListalibros(List<Libro> listalibros) {
        this.listalibros = listalibros;
    }
    @XmlTransient
    public Autor getAutor() {
        return autor;
    }
    @XmlTransient
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public AutorController() {
    }
    public List<AutorPremio> getLista() {
        return lista;
    }

    public void setLista(List<AutorPremio> lista) {
        this.lista = lista;
    }
    

    public Premio getPremio() {
        return premio;
    }

    public void setPremio(Premio premio) {
        this.premio = premio;
    }
    

    public Autor getSelected() {
        if (current == null) {
            current = new Autor();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AutorFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            //de esta manera obtengo el número de autores que hay
            pagination = new PaginationHelper(this.getItemsAvailableSelectOne().length) {

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
        current = (Autor) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Autor();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AutorCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Autor) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AutorUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Autor) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AutorDeleted"));
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

    public SelectItem[] getItemsAvailableSelectMany() {
        //return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        return getSelectAutor(ejbFacade.autoresOrdenados(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        //return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        return getSelectAutor(ejbFacade.autoresOrdenados(), true);
    }
    
    public Autor getAutor(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Autor.class, value="autoresConverter") //hay que darle el value para la validación
    public static class AutorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AutorController controller = (AutorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "autorController");
            return controller.getAutor(getKey(value));
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
            if (object instanceof Autor) {
                Autor o = (Autor) object;
                return getStringKey(o.getCodAutor());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Autor.class.getName());
            }
        }

    }
    public static SelectItem[] getSelectAutor(List<Autor> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("0", "Elige un autor");
            i++;
        }//nombre del objeto : y la lista, todos los objetos del mundo mundial son de la superclase Object*/
        for (Autor x : entities) {
            items[i++] = new SelectItem(x, x.getApellido1() + " " + x.getApellido2() + ", " + x.getNomAutor());
        }
        return items;
    }
    public static int calcularEdad(Date fnac){
        Calendar fechaActual = Calendar.getInstance();
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.setTime(fnac);
        int ano = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
        int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
        if((mes < 0) || ((mes==0) && (dia < 0)))
            ano--;
        return ano;
    }
    public static boolean estaVivo(Autor autor){
        if(autor.getFDef() == null)
            return true;
        return false;
    }
    
    public void cargar_autores_premio(){
        lista = ejbFacade.cargarAutoresPremios(premio);
    }
    public void cargar_datos(){
        listalibros = ejbFacade.cargarLibrosAutor(autor);
        listaPremios = ejbFacade.cargarPremiosAutor(autor);
    }
}
