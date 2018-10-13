package ca.eloas.presentations.streams;

import org.raml.api.RamlMediaType;
import org.raml.api.RamlResourceMethod;
import org.raml.builder.BodyBuilder;
import org.raml.builder.MethodBuilder;
import org.raml.builder.ResponseBuilder;
import org.raml.builder.TypeBuilder;
import org.raml.jaxrs.plugins.TypeHandler;
import org.raml.jaxrs.plugins.TypeSelector;
import org.raml.jaxrs.types.TypeRegistry;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Created. There, you have it.
 */
public class SimplerLoop {
    public void complicated(TypeRegistry typeRegistry, Collection<RamlResourceMethod> methods, TypeSelector selector,
                            MethodBuilder methodBuilder) throws IOException {

        List<BodyBuilder> builder = methods.stream().flatMap((m) -> m.getProducedMediaTypes().stream()).map(mt -> {

            BodyBuilder body = BodyBuilder.body(mt.toStringRepresentation());

      //      TypeHandler typeHandler = selector.pickTypeWriter(null, mt);
      //      TypeBuilder type = typeHandler.writeType(typeRegistry, null);
        //    body.ofType(type);
            return body;
        }).collect(Collectors.toList());

    }
}
