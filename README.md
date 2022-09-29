<p align="center">
  <img src="./docs/assets/meilisearch_kotlin.png" alt="Meilisearch Kotlin" width="200" height="200" />
</p>

<h1 align="center">Meilisearch Kotlin</h1>

<h4 align="center">
  <a href="https://github.com/meilisearch/meilisearch">Meilisearch</a> |
  <a href="https://docs.meilisearch.com">Documentation</a> |
  <a href="https://slack.meilisearch.com">Slack</a> |
  <a href="https://roadmap.meilisearch.com/tabs/1-under-consideration">Roadmap</a> |
  <a href="https://www.meilisearch.com">Website</a> |
  <a href="https://docs.meilisearch.com/faq">FAQ</a>
</h4>

<p align="center">
  <a href="https://maven-badges.herokuapp.com/maven-central/com.meilisearch.sdk/meilisearch-java"><img src="https://maven-badges.herokuapp.com/maven-central/com.meilisearch.sdk/meilisearch-java/badge.svg" alt="Version"></a>
  <a href="https://github.com/cuongnv126/meilisearch-kotlin/blob/main/LICENSE"><img src="https://img.shields.io/badge/license-MIT-informational" alt="License"></a>
</p>

<p align="center">⚡ The Meilisearch API client written for Kotlin ☕️</p>

**Meilisearch Kotlin** is the Meilisearch API client for Kotlin/Java developers.

**Meilisearch** is an open-source search
engine. [Discover what Meilisearch is!](https://github.com/meilisearch/meilisearch)

## Table of Contents <!-- omit in toc -->

- [📖 Documentation](#-documentation)
- [🔧 Installation](#-installation)
- [🚀 Getting Started](#-getting-started)
- [🛠 Customization](#-customization)
- [🤖 Compatibility with Meilisearch](#-compatibility-with-meilisearch)
- [💡 Learn More](#-learn-more)
- [⚙️ Development Workflow and Contributing](#️-development-workflow-and-contributing)

## 📖 Documentation

See our [Documentation](https://docs.meilisearch.com/learn/tutorials/getting_started.html) or
our [API References](https://docs.meilisearch.com/reference/api/).

## 🔧 Installation

`meilisearch-java` is available from JCentral official repository. To be able to import this package, declare it as a
dependency in your project:

### Maven <!-- omit in toc -->

Add the following code to the `<dependencies>` section of your project:

```xml

<dependency>
  <groupId>com.meilisearch.sdk</groupId>
  <artifactId>meilisearch-java</artifactId>
  <version>0.7.2</version>
  <type>pom</type>
</dependency>
```

### Gradle <!-- omit in toc -->

Add the following line to the `dependencies` section of your `build.gradle`:

```groovy
implementation 'com.meilisearch.sdk:meilisearch-java:0.7.2'
```

### Run Meilisearch <!-- omit in toc -->

There are many easy ways
to [download and run a Meilisearch instance](https://docs.meilisearch.com/reference/features/installation.html#download-and-launch)
.

For example, using the `curl` command
in [your Terminal](https://itconnect.uw.edu/learn/workshops/online-tutorials/web-publishing/what-is-a-terminal/):

```bash
 # Install Meilisearch
 curl -L https://install.meilisearch.com | sh

 # Launch Meilisearch
 ./meilisearch --master-key=masterKey
 ```

NB: you can also download Meilisearch from **Homebrew** or **APT** or even run it using **Docker**.

## 🚀 Getting Started

#### Add documents <!-- omit in toc -->

```kotlin
object TestMeilisearch {

  @JvmStatic
  fun main(args: Array<String>) {
    val array = JSONArray()
    val items = object : ArrayList<JSONObject>() {
      init {
        add(JSONObject().put("id", "1").put("title", "Carol").put("genres", JSONArray("[\"Romance\",\"Drama\"]")))
        add(JSONObject().put("id", "2").put("title", "Wonder Woman").put("genres", JSONArray("[\"Action\",\"Adventure\"]")))
        add(JSONObject().put("id", "3").put("title", "Life of Pi").put("genres", JSONArray("[\"Adventure\",\"Drama\"]")))
        add(JSONObject().put("id", "4").put("title", "Mad Max: Fury Road").put("genres", JSONArray("[\"Adventure\",\"Science Fiction\"]")))
        add(JSONObject().put("id", "5").put("title", "Moana").put("genres", JSONArray("[\"Fantasy\",\"Action\"]")))
        add(JSONObject().put("id", "6").put("title", "Philadelphia").put("genres", JSONArray("[\"Drama\"]")))
      }
    }
    array.put(items)

    val documents = array.getJSONArray(0).toString()
    val client = MeiliClient(Config("http://localhost:7700", "masterKey"))

    // An index is where the documents are stored.
    val index = client.index("movies")

    // If the index 'movies' does not exist, Meilisearch creates it when you first add the documents.
    index.addDocuments(documents) // => { "uid": 0 }
  }
}
```

With the `taskUid`, you can check the status (`enqueued`, `processing`, `succeeded` or `failed`) of your documents
addition using the [task endpoint](https://docs.meilisearch.com/reference/api/tasks.html#get-task).

#### Basic Search <!-- omit in toc -->

A basic search can be performed by calling `index.search()` method, with a simple string query.

```java
import com.meilisearch.sdk.model.SearchResult;

// Meilisearch is typo-tolerant:
SearchResult results=index.search("carlo");
  System.out.println(results);
```

- Output:

```
SearchResult(hits=[{id=1.0, title=Carol, genres:[Romance, Drama]}], offset=0, limit=20, nbHits=1, exhaustiveNbHits=false, facetsDistribution=null, exhaustiveFacetsCount=false, processingTimeMs=3, query=carlo)
```

#### Custom Search <!-- omit in toc -->

If you want a custom search, the easiest way is to create a `SearchRequest` object, and set the parameters that you
need.<br>
All the supported options are described in
the [search parameters](https://docs.meilisearch.com/reference/features/search_parameters.html) section of the
documentation.

```kotlin
val results = index.search(
  SearchRequest("max")
    .setOffset(0)
    .setLimit(4)
)
println(results.hits)
```

- Output:

```json
[
  {
    "id": 3,
    "title": "Life of Pi",
    "genres": [
      "Adventure",
      "Drama"
    ],
    "_formatted": {
      "id": 3,
      "title": "Life <em>of</em> Pi",
      "genres": [
        "Adventure",
        "Drama"
      ]
    },
    "_matchesInfo": {
      "title": [
        {
          "start": 5,
          "length": 2
        }
      ]
    }
  }
]
```

#### Custom Search With Filters <!-- omit in toc -->

If you want to enable filtering, you must add your attributes to the `filterableAttributes` index setting.

```kotlin
index.updateFilterableAttributesSettings(
  arrayOf(
    "id",
    "genres"
  )
)
```

You only need to perform this operation once.

Note that Meilisearch will rebuild your index whenever you update `filterableAttributes`. Depending on the size of your
dataset, this might take time. You can track the process using
the [task status](https://docs.meilisearch.com/reference/api/tasks.html#get-task).

Then, you can perform the search:

```kotlin
index.search(
  SearchRequest("wonder")
    .setFilter(arrayOf("id > 1 AND genres = Action"))
)
```

```json
{
  "hits": [
    {
      "id": 2,
      "title": "Wonder Woman",
      "genres": [
        "Action",
        "Adventure"
      ]
    }
  ],
  "offset": 0,
  "limit": 20,
  "nbHits": 1,
  "processingTimeMs": 0,
  "query": "wonder"
}
```

## 🛠 Customization

### JSON <!-- omit in toc -->

#### Creating a Custom `JsonHandler` <!-- omit in toc -->

To create a custom JSON handler, create an object of JsonHandle in `jsonHandlerFactory` config.<br>

```kotlin
val client = MeiliClient(
  Config("http://127.0.0.1:7700")
    .jsonHandlerFactory(object : JsonHandlerFactory {
      private val jacksonJsonHandler = JacksonJsonHandler()
      override fun newJsonHandler(): JsonHandler = jacksonJsonHandler // singleton
    })
)
```

### Custom Client <!-- omit in toc -->

To create a custom `Client` handler, create an object of `Client` and set the required parameters.

A `Config` object should be passed, containing your host URL and your API key.

```kotlin
val client = MeiliClient(
  Config("http://127.0.0.1:7700")
    .httpClientFactory(object : HttpClientFactory {
      override fun newHttpClient(): AbstractHttpClient {
        return OkHttp3Client() // build-in
      }
    })
)
```

## 🤖 Compatibility with Meilisearch

This package only guarantees compatibility with
the [version v0.27.0 of Meilisearch](https://github.com/meilisearch/meilisearch/releases/tag/v0.27.0).

## 💡 Learn More

The following sections may interest you:

- **Manipulate documents**: see the [API references](https://docs.meilisearch.com/reference/api/documents.html) or read
  more about [documents](https://docs.meilisearch.com/learn/core_concepts/documents.html).
- **Search**: see the [API references](https://docs.meilisearch.com/reference/api/search.html) or follow our guide
  on [search parameters](https://docs.meilisearch.com/reference/features/search_parameters.html).
- **Manage the indexes**: see the [API references](https://docs.meilisearch.com/reference/api/indexes.html) or read more
  about [indexes](https://docs.meilisearch.com/learn/core_concepts/indexes.html).
- **Configure the index settings**: see the [API references](https://docs.meilisearch.com/reference/api/settings.html)or
  follow our guide on [settings parameters](https://docs.meilisearch.com/reference/features/settings.html).

## ⚙️ Development Workflow and Contributing

Any new contribution is more than welcome in this project!

If you want to know more about the development workflow or want to contribute, please visit
our [contributing guidelines](/CONTRIBUTING.md) for detailed instructions!

<hr>

**Meilisearch** provides and maintains many **SDKs and Integration tools** like this one. We want to provide everyone
with an **amazing search experience for any kind of project**. If you want to contribute, make suggestions, or just know
what's going on right now, visit us in the [integration-guides](https://github.com/meilisearch/integration-guides)
repository.
