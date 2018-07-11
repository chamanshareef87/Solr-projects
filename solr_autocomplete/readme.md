Create core
1. Add suggest search component solrconfig.xml

  <searchComponent name="suggest" class="solr.SuggestComponent">
	  <lst name="suggester">
		<str name="name">mySuggester</str>
		<str name="lookupImpl">FuzzyLookupFactory</str>
		<str name="dictionaryImpl">DocumentDictionaryFactory</str>
		<str name="field">name</str>
		<str name="weightField">price</str>
		<str name="suggestAnalyzerFieldType">string</str>
		<str name="buildOnStartup">true</str>
	  </lst>
  </searchComponent>

  2. Add request handler in solrconfig.xml
    <requestHandler name="/suggest" class="solr.SearchHandler" startup="lazy">
	  <lst name="defaults">
		<str name="suggest">true</str>
		<str name="suggest.count">10</str>
		<str name="suggest.dictionary">mySuggester</str>   
	  </lst>
	  <arr name="components">
		<str>suggest</str>
	  </arr>
  </requestHandler>
  
  3. In schema I have two fied defined
   <field name="name" type="text_general" indexed="true" stored="true" multiValued="false" />
 <field name="description" type="text_general" indexed="true" stored="true" multiValued="false" />
  
  4. Index data 
  5. Search 
http://localhost:8983/solr/books_suggester1/suggest?q=The&wt=json&indent=true


