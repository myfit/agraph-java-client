/******************************************************************************
** Copyright (c) 2008-2009 Franz Inc.
** All rights reserved. This program and the accompanying materials
** are made available under the terms of the Eclipse Public License v1.0
** which accompanies this distribution, and is available at
** http://www.eclipse.org/legal/epl-v10.html
******************************************************************************/

package com.franz.agraph.jena;

public class AGQueryFactory {

	public static AGQuery create(String queryString) {
    	AGQuery query = new AGQuery(queryString);
        return query ;
    }

}
