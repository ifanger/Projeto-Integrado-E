package projetoe.minhamemoria.models;

public class ListItem {
    private long id;
    private long listId;
    private String title;
    private boolean checked;

    public ListItem(long listId, String title) throws Exception {
        setListId(listId);
        setTitle(title);
        setChecked(0);
        this.id = -1;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) throws Exception {
        if(listId < 0)
            throw new Exception("A id da lista é inválido.");

        this.listId = listId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception {
        if(title.length() < 1)
            throw new Exception("O título do item da lista é inválido.");

        this.title = title;
    }

    public int isChecked() { return (checked)? 1 : 0; }

    public void setChecked(int checked) { this.checked = checked == 1; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListItem list = (ListItem) o;

        return id == list.id && (title != null ? title.equals(list.title) : list.title == null);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "id=" + id +
                ", listId=" + listId +
                ", title='" + title + '\'' +
                '}';
    }
}
