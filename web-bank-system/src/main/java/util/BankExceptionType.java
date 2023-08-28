package util;

public enum BankExceptionType {
	PRODUCTNOTFOUND((byte)1, "El producto no fue encontrado"), PRODUCTTYPENOTDECLARED((byte)2, 
			"El tipo de producto no se encuentra en ninguna de nuestras listas"), 
	WRONGFORMAT((byte)3, "El formato que se ingreso es incorrecto"), 
	USERNOTFOUND((byte)4, "El usuario no fue encontrado, crea una cuenta en su lugar"), 
	DUPLICATEUSER((byte)5, "El usuario ya existe con esos datos"), 
	NOTENOUGHMONEY((byte)6, "El usuario al que se le quiso retirar el dinero"), 
	TRANSACTIONFAILED((byte)7, "La transaccion tuvo un fallo"),
	CARDNOTFOUND((byte)8, "El numero de tarjeta no coincide con ninguna en nuestro sistema"),
	DAOSQLSERVER((byte)9, "Hubo un error con la operacion del dao"),
	UNKOWNERROR((byte)100, "error desconocido en el banco");
	
	private Byte code;
	private String message;
	
	private BankExceptionType(byte code, String message){
		this.code = Byte.valueOf(code);
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
