package br.com.gointerop.hapi.fhir.mapper;

public class MapperPatient extends Mapper {
	private static MapperPatient instance;
	
	private static final String TABLE_NAME = "tb_cidadao";
	private static final String PRIMARY_KEY = "co_seq_cidadao";
	
	public static final String _id = "co_seq_cidadao";
	public static final String _language = null;
	public static final String birthdate = "dt_nascimento";
	public static final String deceased = "dt_obito";
	public static final String addressState = "co_uf";
	public static final String gender = "co_sexo";
	public static final String link = null;
	public static final String language = null;
	public static final String addressCountry = null;
	public static final String deathDate = "dt_obito";
	public static final String phonetic = null;
	public static final String telecom = "nu_telefone_residencial";
	public static final String addressCity = "co_localidade";
	public static final String email = "ds_email";
	public static final String given = "no_cidadao";
	public static final String identifier = "nu_cpf";
	public static final String address = "ds_logradouro";
	public static final String generalPractitioner = null;
	public static final String active = "st_ativo_para_exibicao";
	public static final String addressPostalCode = "ds_cep";
	public static final String phone = "nu_telefone_celular";
	public static final String organization = null;
	public static final String addressUse = "ds_logradouro";
	public static final String name = "no_cidadao";
	public static final String family = "no_cidadao";
	
	public static final MapperPatient getInstance() {
		if(MapperPatient.instance == null) MapperPatient.instance = new MapperPatient();
		
		return MapperPatient.instance;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}
}
