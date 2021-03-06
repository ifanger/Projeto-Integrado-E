package projetoe.minhamemoria.models;

public class Contact {
    private long id;
    private String name;
    private String number;

    public Contact(String name, String number) throws Exception {
        setId(-1);
        setName(name);
        setNumber(number);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name == null)
            throw new Exception("O nome do contato não pode ser null.");

        if(name.length() < 2)
            throw new Exception("O nome de contato não pode ser menor do que 2 caracteres.");

        if(name.length() > 25)
            throw new Exception("O nome de contato não pode ser maior do que 25 caracteres.");

        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws Exception {
        if(number == null)
            throw new Exception("O número do contato não pode ser null.");

        if(number.length() < 8)
            throw new Exception("O número do contato não pode ser menor do que 8 caracteres.");

        if(number.length() > 25)
            throw new Exception("O número do contato não pode ser maior do que 25 caracteres.");

        if(getLongNumber(number) == 0)
            throw new Exception("O número do telefone é inválido. Tente, por exemplo: (19) 3839-8492");

        this.number = number;
    }

    public long getLongNumber(String number) {
        try {
            return Long.parseLong(((number == null) ? this.number : number)
                    .replace("(", "")
                    .replace(")", "")
                    .replace("+", "")
                    .replace("-", "")
                    .replace(" ", "")
                    .trim()
            );
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;
        return number != null ? number.equals(contact.number) : contact.number == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + number.hashCode();
        return result;
    }
}
