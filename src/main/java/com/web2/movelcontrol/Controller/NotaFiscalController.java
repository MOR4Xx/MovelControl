/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */
package com.web2.movelcontrol.Controller;


import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.NotaFiscalRequestDTO;
import com.web2.movelcontrol.DTO.NotaFiscalResponseDTO;
import com.web2.movelcontrol.Model.NotaFiscal;
import com.web2.movelcontrol.Service.NotaFiscalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nota-fiscal")
@Tag(name = "Nota Fiscal", description = "Operações relacionadas as Notas Fiscais")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService service;

    @Operation(summary = "Criar uma nova nota fiscal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota fiscal criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotaFiscalResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping(value = "/criar"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotaFiscalResponseDTO> criarNotaFiscal(@RequestBody @Valid NotaFiscalRequestDTO notaFIscalRequestDTO) {
        NotaFiscal notaFiscal = DataMapper.parseObject(notaFIscalRequestDTO, NotaFiscal.class);

        service.create(notaFiscal);

        return ResponseEntity.ok(DataMapper.parseObject(notaFiscal, NotaFiscalResponseDTO.class));
    }

    @Operation(summary = "Buscar nota fiscal por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota fiscal encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotaFiscalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalResponseDTO> findById(@PathVariable Long id) {
        NotaFiscal notaFiscal = service.findById(id);

        return ResponseEntity.ok(DataMapper.parseObject(notaFiscal, NotaFiscalResponseDTO.class));
    }

    @Operation(summary = "Atualizar uma nota fiscal existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota fiscal atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotaFiscalRequestDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada", content = @Content)
    })
    @PutMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NotaFiscal atualizarNotaFiscal(@PathVariable Long id, @RequestBody NotaFiscalRequestDTO notaFIscalRequestDTO) {
        NotaFiscal notaFiscal = DataMapper.parseObject(notaFIscalRequestDTO, NotaFiscal.class);

        return service.update(notaFiscal.getId(), notaFiscal);
    }

    @Operation(summary = "Deletar uma nota fiscal por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nota fiscal deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada", content = @Content)
    })
    @DeleteMapping(value = "/deletar/{id}")
    public void deletarNotaFiscal(@PathVariable Long id) {
        service.delete(id);
    }
}
