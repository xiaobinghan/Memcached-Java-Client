/**
 * UnitTests.java
 * Test class for testing memcached java client.
 *
 * Copyright (c) 2005
 * Kevin Burton
 *
 * See the memcached website:
 * http://www.danga.com/memcached/
 *
 * This module is Copyright (c) 2005 Kevin Burton
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later
 * version.
 *
 * This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 *
 * @author Kevin Burton
 * @author greg whalin <greg@meetup.com> 
 * @version 1.2
 */
package com.danga.MemCached.test;

import com.danga.MemCached.*;
import java.util.*;

public class UnitTests {

    public static MemCachedClient mc = null;

    public static void test1() throws Exception {

        mc.set( "foo", Boolean.TRUE );
        Boolean b = (Boolean)mc.get( "foo" );

        //FIXME: assert the value is correct

        if ( ! b.booleanValue() )
            throw new Exception();

    }

    public static void test2() throws Exception {

        mc.set( "foo", new Integer( Integer.MAX_VALUE ) );
        Integer i = (Integer)mc.get( "foo" );

        //FIXME: assert the value is correct

        if ( i.intValue() != Integer.MAX_VALUE )
            throw new Exception();
        
    }

    public static void test3() throws Exception {

        String input = "test of string encoding";
        
        mc.set( "foo", input );
        String s = (String)mc.get( "foo" );

        //FIXME: assert the value is correct

        //if ( i.intValue() != Integer.MAX_VALUE )
        //throw new Exception();
        
        if ( s.equals( input ) == false )
            throw new Exception();
        
    }
    
    public static void test4() throws Exception {

        mc.set( "foo", new Character( 'z' ) );
        Character c = (Character)mc.get( "foo" );

        if ( c.charValue() != 'z' )
            throw new Exception();
        
    }

    public static void test5() throws Exception {

        mc.set( "foo", new Byte( (byte)127 ) );
        Byte b = (Byte)mc.get( "foo" );

        if ( b.byteValue() != 127 )
            throw new Exception();
        
    }

    public static void test6() throws Exception {

        mc.set( "foo", new StringBuffer( "hello" ) );
        StringBuffer o = (StringBuffer)mc.get( "foo" );

        if ( o.toString().equals( "hello" ) == false )
            throw new Exception();
        
    }

    public static void test7() throws Exception {

        mc.set( "foo", new Short( (short)100 ) );
        Short o = (Short)mc.get( "foo" );

        if ( o.shortValue() != 100 )
            throw new Exception();
        
    }

    public static void test8() throws Exception {

        mc.set( "foo", new Long( Long.MAX_VALUE ) );
        Long o = (Long)mc.get( "foo" );

        if ( o.longValue() != Long.MAX_VALUE )
            throw new Exception();

        mc.getCounter( "foo" );

    }

    public static void test9() throws Exception {

        mc.set( "foo", new Double( 1.1 ) );
        Double o = (Double)mc.get( "foo" );

        if ( o.doubleValue() != 1.1 )
            throw new Exception();
        
    }

    public static void test10() throws Exception {

        mc.set( "foo", new Float( 1.1f ) );
        Float o = (Float)mc.get( "foo" );

        System.out.println( o.floatValue() );
        
        if ( o.floatValue() != 1.1f )
            throw new Exception();
        
    }

    public static void test11() throws Exception {

        mc.set( "foo", new Integer( 100 ) );
        //Float o = (Float)mc.get( "foo" );

        long l = mc.decr( "foo" );

        //if ( l != 99 )
        //    throw new Exception( "" + l );

    }

    public static void test12() throws Exception {

        mc.set( "foo", new Integer( 100 ), new Date( System.currentTimeMillis() ));

        Thread.sleep( 1000 );
        
        if ( mc.get( "foo" ) != null )
            throw new Exception();
        
    }

	public static void test13() throws Exception {

		long i = 0;
		mc.storeCounter("foo", i);
		mc.incr("foo"); // foo now == 1
		mc.incr("foo", (long)5); // foo now == 6
		long j = mc.decr("foo", (long)2); // foo now == 4

		if (j != 4)
			throw new Exception();
	}

	public static void test14() throws Exception {
		Date d1 = new Date();
		mc.set("foo", d1);

		Date d2 = (Date) mc.get("foo");

		if (d1.compareTo(d2) != 0)
			throw new Exception("date mismatch");
	}
    
	/**
	 * This runs through some simple tests of the MemCacheClient.
	 *
	 * Command line args:
	 * args[0] = number of threads to spawn
	 * args[1] = number of runs per thread
	 * args[2] = size of object to store 
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {

		String[] serverlist = { "cache0.int.meetup.com:1624"  };

		// initialize the pool for memcache servers
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers( serverlist );

		pool.initialize();

        mc = new MemCachedClient();
        mc.setCompressEnable( false );

        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
        test11();
        test12();
		test13();
		test14();

        //FIXME: test replace()

        //FIXME: test expiration..
	}
}