package org.openrdf.repository.sail;

import java.util.List;

import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.BNodeImpl;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;

import franz.exceptions.SoftException;

public class AllegroStatement extends StatementImpl {

	private URI subject = null;
	private URI predicate = null;
	private Value object = null;
	private URI context = null;
	private List<String> stringTuple = null;

	public AllegroStatement(URI subject, URI predicate, Value object) {
		super(subject, predicate, object);
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	protected void setQuad(List<String> stringTuple) {
		this.stringTuple = stringTuple;
	}
	
	public URI getSubject() {
		if (this.subject == null) {
			this.subject = (URI)this.stringTermToTerm(this.stringTuple.get(0));
		}
		return this.subject;
	}

	public URI getPredicate() {
		if (this.predicate == null) {
			this.predicate = (URI)this.stringTermToTerm(this.stringTuple.get(1));
		}
		return this.predicate;
	}

	public Value getObject() {
		if (this.object == null) {
			this.object = (Value)this.stringTermToTerm(this.stringTuple.get(2));
		}
		return this.object;
	}

	public URI getContext() {
		if (this.context == null) {
			if (this.stringTuple.size() > 3) {
				this.context = (URI)this.stringTermToTerm(this.stringTuple.get(3));
			} else {
				this.context = null;
			}
		}
		return this.context;
	}

	/**
     * Given a string representing a term in ntriples format, return
     * a URI, Literal, or BNode.
	 */
    protected static Value stringTermToTerm(String stringTerm) {
        if (stringTerm == null) return null;
        if (stringTerm.charAt(0) == '<') {
            String uri = stringTerm.substring(1, stringTerm.length() - 1);
            return new URIImpl(uri);
        }
        else if (stringTerm.charAt(0) == '"') {
            // we have a double-quoted literal with either a data type or a language indicator
            int caratPos = stringTerm.indexOf("^^");
            if (caratPos >= 0) {
                String label = stringTerm.substring(1, caratPos - 1);
                String dType = stringTerm.substring(caratPos + 2);
                URI datatype = (URI)stringTermToTerm(dType);
                return new LiteralImpl(label, datatype);
            }
            int atPos = stringTerm.indexOf('@');
            if (atPos >=0) {
                String label = stringTerm.substring(1,atPos - 1);
                String language = stringTerm.substring(atPos + 1);
                return new LiteralImpl(label, language);
            } else {
                return new LiteralImpl(stringTerm.substring(1, stringTerm.length() - 1));
            }
        } else if (stringTerm.charAt(0) == '_' && stringTerm.charAt(1) == ':') {
        	return new BNodeImpl(stringTerm.substring(2));
        } else {
        	throw new SoftException("Cannot translate '" + stringTerm + "' into an OpenRDF term.");
        }
    }
        
    /**
     * Given a string representing a term in ntriples format, return
     * a URI or the label portion of a literal (a string minus the double quotes).
     * TODO: IMPLEMENT BNODES
     */
    protected static String ntriplesStringToStringValue(String stringTerm) {
        if (stringTerm == null) return null;
        if (stringTerm.charAt(0) == '<') {
            String uri = stringTerm.substring(1, stringTerm.length() - 1);
            return uri;
        }
        else if (stringTerm.charAt(0) == '"') {
            // we have a double-quoted literal with either a data type or a language indicator
            int caratPos = stringTerm.indexOf("^^");
            if (caratPos >= 0) {
                String label = stringTerm.substring(1, caratPos - 1);
                return label;
            }
            int atPos = stringTerm.indexOf('@');
            if (atPos >=0) {
                String label = stringTerm.substring(1,atPos - 1);
                return label;
            } else {
                return stringTerm.substring(1, stringTerm.length() - 1);
            }
        } else {
        	throw new SoftException("Cannot translate '" + stringTerm + "' into a string value.");
        }
    }
                

}